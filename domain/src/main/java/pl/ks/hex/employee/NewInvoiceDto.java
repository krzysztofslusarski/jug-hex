package pl.ks.hex.employee;

import lombok.Builder;
import lombok.Value;
import pl.ks.hex.common.model.Money;

@Value
@Builder
public class NewInvoiceDto {
    Money payment;
}
