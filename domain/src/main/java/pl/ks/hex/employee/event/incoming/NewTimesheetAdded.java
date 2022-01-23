package pl.ks.hex.employee.event.incoming;

import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.ks.hex.common.event.DomainIncomingEvent;
import pl.ks.hex.common.model.WorkHours;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NewTimesheetAdded implements DomainIncomingEvent {
    Instant when;
    Integer sequenceNumber;

    WorkHours hours;
}
