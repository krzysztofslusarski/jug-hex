package pl.ks.hex.employee;

import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "employeeId")
class EmployeeEventStream {
    @Id
    private UUID employeeId;

    @Version
    private Long version;

    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "fk_stream")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<EmployeeEvent> events;
}
