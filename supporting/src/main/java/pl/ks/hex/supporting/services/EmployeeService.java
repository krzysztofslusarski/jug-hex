package pl.ks.hex.supporting.services;

import java.math.BigDecimal;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ks.hex.supporting.dao.Employee;
import pl.ks.hex.supporting.dao.EmployeeRepository;

@Component
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Employee createNew(String firstName, String lastName, BigDecimal hourlyEarnings) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setHourlyEarnings(hourlyEarnings);
        employee.setId(UUID.randomUUID());
        return employeeRepository.save(employee);
    }

    @Transactional
    public void addNewTimesheet(UUID employeeId, int hours) {
        Employee employee = employeeRepository.getById(employeeId);
        if (employee.getLastNotSettledTimesheetWorkTime() != null) {
            throw new IllegalArgumentException("You need to first settle the invoice");
        }
        employee.setLastNotSettledTimesheetWorkTime(hours);
    }
}
