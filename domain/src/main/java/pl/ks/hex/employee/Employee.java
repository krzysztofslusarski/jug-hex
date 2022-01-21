package pl.ks.hex.employee;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.ks.hex.common.event.DomainIncomingEvent;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.common.model.WorkHours;
import pl.ks.hex.employee.event.incoming.Created;
import pl.ks.hex.employee.event.incoming.InvoiceSettled;
import pl.ks.hex.employee.event.incoming.NewTimesheetReported;

@Getter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Employee {
    private EmployeeId id;
    private FirstName firstName;
    private LastName lastName;
    private WorkHours lastNotSettledTimesheetWorkTime;
    private Money hourlyRate;

    private List<DomainIncomingEvent> pendingEvents = new ArrayList<>();
    private int currentSeqNumber = 0;
    private long version;

    @Builder
    public Employee(EmployeeId id, FirstName firstName, LastName lastName, Money hourlyRate) {
        DomainIncomingEvent event = Created.builder()
                .firstName(firstName)
                .lastName(lastName)
                .hourlyRate(hourlyRate)
                .sequenceNumber(currentSeqNumber++)
                .when(Instant.now())
                .id(id)
                .build();
        handleWithAppend(event);
    }

    private void created(Created created) {
        this.id = created.getId();
        this.firstName = created.getFirstName();
        this.lastName = created.getLastName();
        this.hourlyRate = created.getHourlyRate();
    }

    public void reportTimesheet(NewTimesheetDto newTimesheet) {
        if (lastNotSettledTimesheetWorkTime != null) {
            throw new IllegalArgumentException("There is not settled timesheet");
        }

        DomainIncomingEvent event = NewTimesheetReported.builder()
                .sequenceNumber(currentSeqNumber++)
                .when(Instant.now())
                .workTimeHours(newTimesheet.getWorkTimeHours())
                .build();
        handleWithAppend(event);
    }

    private void newTimesheetReported(NewTimesheetReported newTimesheetReported) {
        lastNotSettledTimesheetWorkTime = newTimesheetReported.getWorkTimeHours();
    }

    public void settleInvoice(NewInvoiceDto newInvoice) {
        if (lastNotSettledTimesheetWorkTime == null) {
            throw new IllegalArgumentException("There is no timesheet to settle");
        }

        Money invoiceProperAmount = hourlyRate.multiply(lastNotSettledTimesheetWorkTime.getValue());
        if (newInvoice.getPayment().compareTo(invoiceProperAmount) != 0) {
            throw new IllegalArgumentException("Illegal invoice payment amount");
        }

        DomainIncomingEvent event = InvoiceSettled.builder()
                .sequenceNumber(currentSeqNumber++)
                .when(Instant.now())
                .payment(newInvoice.getPayment())
                .build();
        handleWithAppend(event);
    }

    private void invoiceSettled(InvoiceSettled invoiceSettled) {
        lastNotSettledTimesheetWorkTime = null;
    }

    public static Employee recreate(List<DomainIncomingEvent> events, long version) {
        Employee employee = new Employee();
        events.forEach(employee::handle);
        employee.currentSeqNumber = events.get(events.size() - 1).getSequenceNumber() + 1;
        employee.version = version;
        return employee;
    }

    private void handleWithAppend(DomainIncomingEvent event) {
        pendingEvents.add(event);
        handle(event);
    }

    private void handle(DomainIncomingEvent event) {
        if (event instanceof Created e) {
            created(e);
        } else if (event instanceof NewTimesheetReported e) {
            newTimesheetReported(e);
        } else if (event instanceof InvoiceSettled e) {
            invoiceSettled(e);
        }
    }
}
