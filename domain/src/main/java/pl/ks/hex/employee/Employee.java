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
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.common.model.WorkHours;
import pl.ks.hex.employee.event.incoming.Created;
import pl.ks.hex.employee.event.incoming.NewInvoiceAdded;
import pl.ks.hex.employee.event.incoming.NewTimesheetAdded;

@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {
    private int sequenceNumber;
    private List<DomainIncomingEvent> pendingEvents = new ArrayList<>();

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
        Created created = Created.builder()
                .when(Instant.now())
                .sequenceNumber(sequenceNumber++)
                .id(EmployeeId.of(UUID.randomUUID()))
                .firstName(firstName)
                .lastName(lastName)
                .hourlyEarnings(hourlyEarnings)
                .build();
        handleWithAppend(created);
    }

    private void handle(Created created) {
        this.id = created.getId();
        this.firstName = created.getFirstName();
        this.lastName = created.getLastName();
        this.hourlyEarnings = created.getHourlyEarnings();
    }

    public void addNewTimesheet(WorkHours hours) {
        if (lastNotSettledTimesheetWorkTime != null) {
            throw new IllegalArgumentException("You need to add the invoice");
        }
        NewTimesheetAdded newTimesheetAdded = NewTimesheetAdded.builder()
                .when(Instant.now())
                .sequenceNumber(sequenceNumber++)
                .hours(hours)
                .build();
        handleWithAppend(newTimesheetAdded);
    }

    private void handle(NewTimesheetAdded newTimesheetAdded) {
        lastNotSettledTimesheetWorkTime = newTimesheetAdded.getHours();
    }

    public void addNewInvoice(Money payment) {
        if (lastNotSettledTimesheetWorkTime == null) {
            throw new IllegalArgumentException("You need to first add a new timesheet");
        }
        Money toInvoice = hourlyEarnings.multiply(lastNotSettledTimesheetWorkTime.getValue());
        if (payment.compareTo(toInvoice) != 0) {
            throw new IllegalArgumentException("Wrong value on invoice, should be: " + toInvoice);
        }

        NewInvoiceAdded newInvoiceAdded = NewInvoiceAdded.builder()
                .when(Instant.now())
                .sequenceNumber(sequenceNumber++)
                .payment(payment)
                .build();
        handleWithAppend(newInvoiceAdded);
    }

    private void handle(NewInvoiceAdded newInvoiceAdded) {
        lastNotSettledTimesheetWorkTime = null;
    }

    private void handleWithAppend(DomainIncomingEvent event) {
        pendingEvents.add(event);
        handleDispatcher(event);
    }

    private void handleDispatcher(DomainIncomingEvent event) {
        if (event instanceof Created created) {
            handle(created);
        } else if (event instanceof NewTimesheetAdded newTimesheetAdded) {
            handle(newTimesheetAdded);
        } else if (event instanceof NewInvoiceAdded newInvoiceAdded) {
            handle(newInvoiceAdded);
        }
    }

    static Employee recreate(List<DomainIncomingEvent> events, Long version) {
        Employee employee = new Employee(version, events.size());
        events.forEach(employee::handleDispatcher);
        return employee;
    }

    List<DomainIncomingEvent> getAndClearPendingEvents() {
        List<DomainIncomingEvent> ret = pendingEvents;
        pendingEvents = new ArrayList<>();
        return ret;
    }
}
