package pl.ks.hex.supporting.dao;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Employee {
    @Id
    private UUID id;
    @Version
    private Long version;
    private String firstName;
    private String lastName;
    private BigDecimal hourlyEarnings;
    private Integer lastNotSettledTimesheetWorkTime;
}
