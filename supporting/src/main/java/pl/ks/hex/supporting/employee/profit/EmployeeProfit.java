package pl.ks.hex.supporting.employee.profit;

import lombok.*;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.employee.EmployeeId;

import javax.persistence.*;

@Entity
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class EmployeeProfit {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "id"))
    })
    private EmployeeId id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "profit"))
    })
    private Money profit;
}
