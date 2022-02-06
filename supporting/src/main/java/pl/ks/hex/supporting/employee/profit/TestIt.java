package pl.ks.hex.supporting.employee.profit;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.common.model.WorkHours;
import pl.ks.hex.employee.Employee;
import pl.ks.hex.employee.EmployeeRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestIt {
    private final EmployeeRepository employeeRepository;
    private final EmployeeProfitQueryRepository employeeProfitQueryRepository;
    private final PlatformTransactionManager transactionManager;

    @EventListener
    public void test(ApplicationReadyEvent applicationReadyEvent) {
        Employee employee = new Employee(FirstName.of("A"), LastName.of("B"), Money.of(BigDecimal.TEN));
        employee.reportTimesheet(WorkHours.of(1));
        employee.issueInvoice(Money.of(BigDecimal.TEN));
        new TransactionTemplate(transactionManager).execute(status -> {
            employeeRepository.save(employee);
            return null;
        });
        log.info("{}", employeeProfitQueryRepository.findAll());

    }

}
