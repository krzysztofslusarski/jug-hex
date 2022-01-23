package pl.ks.hex.supporting.employee;

import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.common.model.WorkHours;

@Entity
@Getter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {
    @Getter
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "id"))
    })
    private EmployeeId id;

    @Version
    private Long version;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "firstName"))
    })
    private FirstName firstName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "lastName"))
    })
    private LastName lastName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "hourlyEarnings"))
    })
    private Money hourlyEarnings;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "lastNotSettledTimesheetWorkTime"))
    })
    private WorkHours lastNotSettledTimesheetWorkTime;

    public Employee(FirstName firstName, LastName lastName, Money hourlyEarnings) {
        this.id = EmployeeId.of(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
        this.hourlyEarnings = hourlyEarnings;
    }

    public void addNewTimesheet(WorkHours hours) {
        if (lastNotSettledTimesheetWorkTime != null) {
            throw new IllegalArgumentException("You need to add the invoice");
        }
        lastNotSettledTimesheetWorkTime = hours;
    }

    public void addNewInvoice(Money payment) {
        if (lastNotSettledTimesheetWorkTime == null) {
            throw new IllegalArgumentException("You need to first add a new timesheet");
        }
        Money toInvoice = hourlyEarnings.multiply(lastNotSettledTimesheetWorkTime.getValue());
        if (payment.compareTo(toInvoice) != 0) {
            throw new IllegalArgumentException("Wrong value on invoice, should be: " + toInvoice);
        }
        lastNotSettledTimesheetWorkTime = null;
    }
}
