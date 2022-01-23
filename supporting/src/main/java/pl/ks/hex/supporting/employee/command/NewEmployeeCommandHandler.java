package pl.ks.hex.supporting.employee.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import pl.ks.hex.common.command.CommandHandler;
import pl.ks.hex.police.ConvictedStatus;
import pl.ks.hex.police.PoliceService;
import pl.ks.hex.supporting.employee.Employee;
import pl.ks.hex.supporting.employee.EmployeeRepository;

@Component
@RequiredArgsConstructor
class NewEmployeeCommandHandler implements CommandHandler<NewEmployeeCommand> {
    private final EmployeeRepository employeeRepository;
    private final PoliceService policeService;
    private final PlatformTransactionManager transactionManager;

    @Override
    public void handle(NewEmployeeCommand command) {
        ConvictedStatus convictedStatus = policeService.checkForCrimes(command.getFirstName(), command.getLastName());
        if (convictedStatus == ConvictedStatus.CONVICTED) {
            throw new IllegalArgumentException("Cannot hire convicted person");
        }

        Employee employee = new Employee(command.getFirstName(), command.getLastName(), command.getHourlyEarnings());
        new TransactionTemplate(transactionManager).execute(status -> {
            employeeRepository.save(employee);
            return null;
        });
    }
}
