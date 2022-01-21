package pl.ks.hex.supporting.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
class EmployeeConfiguration {
    private final EmployeeRepository employeeRepository;

    @Bean
    EmployeeService employeeService() {
        return new EmployeeServiceImpl(employeeRepository);
    }
}
