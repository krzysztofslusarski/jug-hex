package pl.ks.hex.supporting.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
class EmployeeConfiguration {
    private final EmployeeRepository employeeRepository;

    @Bean
    EmployeeSubService employeeSubService() {
        return new EmployeeSubServiceImpl(employeeRepository);
    }

    @Bean
    EmployeeService employeeService(EmployeeSubService employeeSubService) {
        return new EmployeeServiceImpl(employeeRepository, employeeSubService);
    }
}
