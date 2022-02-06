package pl.ks.hex.employee;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.ks.hex.common.event.DomainIncomingEvent;
import pl.ks.hex.common.event.DomainOutgoingEvent;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.common.model.WorkHours;
import pl.ks.hex.employee.event.incoming.Hired;
import pl.ks.hex.employee.event.incoming.InvoiceIssued;
import pl.ks.hex.employee.event.incoming.TimesheetReported;
import pl.ks.hex.employee.event.outgoing.NewInvoiceConfirmed;

@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {
    private int sequenceNumber;
    private List<DomainIncomingEvent> pendingIncomingEvents = new ArrayList<>();
    private List<DomainOutgoingEvent> pendingOutgoingEvents = new ArrayList<>();

    @Getter(AccessLevel.PACKAGE)
    private EmployeeId id;
    @Getter(AccessLevel.PACKAGE)
    private Long version;

    private FirstName firstName;
    private LastName lastName;
    private Money hourlyEarnings;
    private WorkHours lastNotSettledTimesheetWorkTime;

    private Employee(Long version, int sequenceNumber) {
        this.version = version;
        this.sequenceNumber = sequenceNumber;
    }

    public Employee(FirstName firstName, LastName lastName, Money hourlyEarnings) {
        Hired hired = Hired.builder()
                .when(Instant.now())
                .sequenceNumber(sequenceNumber++)
                .firstName(firstName)
                .lastName(lastName)
                .hourlyEarnings(hourlyEarnings)
                .build();
        handleWithAppend(hired);
    }

    private void handle(Hired hired) {
        this.id = EmployeeId.of(UUID.randomUUID());
        this.firstName = hired.getFirstName();
        this.lastName = hired.getLastName();
        this.hourlyEarnings = hired.getHourlyEarnings();
    }

    public void reportTimesheet(WorkHours hours) {
        if (lastNotSettledTimesheetWorkTime != null) {
            throw new IllegalArgumentException("You need to add the invoice");
        }
        TimesheetReported timesheetReported = TimesheetReported.builder()
                .when(Instant.now())
                .sequenceNumber(sequenceNumber++)
                .hours(hours)
                .build();
        handleWithAppend(timesheetReported);
    }

    private void handle(TimesheetReported timesheetReported) {
        lastNotSettledTimesheetWorkTime = timesheetReported.getHours();
    }

    public void issueInvoice(Money payment) {
        if (lastNotSettledTimesheetWorkTime == null) {
            throw new IllegalArgumentException("You need to first add a new timesheet");
        }
        Money toInvoice = hourlyEarnings.multiply(lastNotSettledTimesheetWorkTime.getValue());
        if (payment.compareTo(toInvoice) != 0) {
            throw new IllegalArgumentException("Wrong value on invoice, should be: " + toInvoice);
        }

        InvoiceIssued invoiceIssued = InvoiceIssued.builder()
                .when(Instant.now())
                .sequenceNumber(sequenceNumber++)
                .payment(payment)
                .build();
        handleWithAppend(invoiceIssued);
    }

    private void handle(InvoiceIssued invoiceIssued) {
        pendingOutgoingEvents.add(NewInvoiceConfirmed.builder()
                .employeeId(id)
                .payment(invoiceIssued.getPayment())
                .build()
        );
        lastNotSettledTimesheetWorkTime = null;
    }

    private void handleWithAppend(DomainIncomingEvent event) {
        pendingIncomingEvents.add(event);
        handleDispatcher(event);
    }

    private void handleDispatcher(DomainIncomingEvent event) {
        if (event instanceof Hired hired) {
            handle(hired);
        } else if (event instanceof TimesheetReported timesheetReported) {
            handle(timesheetReported);
        } else if (event instanceof InvoiceIssued invoiceIssued) {
            handle(invoiceIssued);
        }
    }

    static Employee recreate(List<DomainIncomingEvent> events, Long version) {
        Employee employee = new Employee(version, events.size());
        events.forEach(employee::handleDispatcher);
        employee.pendingOutgoingEvents.clear();
        return employee;
    }

    List<DomainIncomingEvent> getAndClearPendingIncomingEvents() {
        List<DomainIncomingEvent> ret = pendingIncomingEvents;
        pendingIncomingEvents = new ArrayList<>();
        return ret;
    }

    List<DomainOutgoingEvent> getAndClearPendingOutgoingEvents() {
        List<DomainOutgoingEvent> ret = pendingOutgoingEvents;;
        pendingOutgoingEvents = new ArrayList<>();
        return ret;
    }
}
