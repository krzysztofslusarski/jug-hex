package pl.ks.hex.supporting.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
class EmployeeConfiguration {
    private final EmployeePrivateRepository employeePrivateRepository;

    @Bean
    EmployeeQueryRepository employeeQueryRepository() {
        return new EmployeeQueryRepositoryImpl(employeePrivateRepository);
    }

    @Bean
    EmployeeRepository employeeRepository() {
        return employeePrivateRepository;
    }
}
