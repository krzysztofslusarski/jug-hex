package pl.ks.hex.employee;

import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.ks.hex.common.event.DomainIncomingEventJavaSerializer;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
class EmployeeConfiguration {
    private final EmployeeEventStreamRepositoryJpa employeeEventStreamRepositoryJpa;
    private final EntityManager entityManager;

    @Bean
    EmployeeRepository employeeRepository() {
        return new EmployeeEventSourceRepository(entityManager, employeeEventStreamRepositoryJpa, new DomainIncomingEventJavaSerializer());
    }
}
