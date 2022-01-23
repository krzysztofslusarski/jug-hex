package pl.ks.hex.supporting.employee.profit;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.ks.hex.supporting.pmo.PmoService;

@Configuration
@RequiredArgsConstructor
class EmployeeProfitConfiguration {
    private final EmployeeProfitRepository employeeProfitRepository;
    private final PmoService pmoService;

    @Bean
    EmployeeProfitQueryRepository employeeProfitQueryRepository() {
        return employeeProfitRepository;
    }

    @Bean
    EmployeeProfitListener employeeProfitListener() {
        return new EmployeeProfitListener(employeeProfitRepository, pmoService);
    }
}
