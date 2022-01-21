package pl.ks.hex.employee.event.incoming;

import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.ks.hex.common.event.DomainIncomingEvent;
import pl.ks.hex.common.model.Money;

@Value
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class InvoiceSettled implements DomainIncomingEvent {
    Instant when;
    Integer sequenceNumber;
    Money payment;
}
