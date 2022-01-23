package pl.ks.hex.employee.event.outgoing;

import lombok.Builder;
import lombok.Value;
import pl.ks.hex.common.event.DomainOutgoingEvent;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.employee.EmployeeId;

@Value
@Builder
public class NewInvoiceConfirmed implements DomainOutgoingEvent {
    EmployeeId employeeId;
    Money payment;
}
