package pl.ks.hex.supporting.employee;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.common.model.WorkHours;

@RequiredArgsConstructor
class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeSubService employeeSubService;

    @Override
    public EmployeeId createNew(FirstName firstName, LastName lastName, Money hourlyEarnings) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setHourlyEarnings(hourlyEarnings);
        employee.setId(EmployeeId.of(UUID.randomUUID()));
        return employeeRepository.save(employee).getId();
    }

    @Override
    public void addNewTimesheet(EmployeeId employeeId, WorkHours hours) {
        Employee employee = employeeRepository.getById(employeeId);
        if (employee.getLastNotSettledTimesheetWorkTime() != null) {
            throw new IllegalArgumentException("You need to first settle the invoice");
        }
        employee.setLastNotSettledTimesheetWorkTime(hours);
        employeeRepository.save(employee);
    }

    @Override
    public void settleNewInvoice(EmployeeId employeeId, Money payment) {
        Employee employee = employeeRepository.getById(employeeId);
        if (employee.getLastNotSettledTimesheetWorkTime() == null) {
            throw new IllegalArgumentException("You need to first add a new timesheet");
        }
        Money toInvoice = employee.getHourlyEarnings().multiply(employee.getLastNotSettledTimesheetWorkTime().getValue());
        if (payment.compareTo(toInvoice) != 0) {
            throw new IllegalArgumentException("Wrong value on invoice, should be: " + toInvoice);
        }
        employee.setLastNotSettledTimesheetWorkTime(null);
        employeeRepository.save(employee);
    }
}
