package pl.ks.hex.supporting.pmo;

import java.math.BigDecimal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.ks.hex.common.model.Money;

@Configuration
class PmoConfiguration {
    @Bean
    PmoService pmoService() {
        return (employeeId, invoicePremium) -> Money.of(BigDecimal.TEN);
    }
}
