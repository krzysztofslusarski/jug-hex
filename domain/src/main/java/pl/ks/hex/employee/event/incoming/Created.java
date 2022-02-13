package pl.ks.hex.employee.event.incoming;

import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.ks.hex.common.event.DomainIncomingEvent;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.employee.EmployeeId;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Created implements DomainIncomingEvent {
    Instant when;
    Integer sequenceNumber;

    EmployeeId id;
    FirstName firstName;
    LastName lastName;
    Money hourlyEarnings;
}
