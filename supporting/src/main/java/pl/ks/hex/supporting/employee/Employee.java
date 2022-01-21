package pl.ks.hex.supporting.employee;

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
import lombok.Setter;
import lombok.ToString;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.common.model.WorkHours;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Employee {
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
}
