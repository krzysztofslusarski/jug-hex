package pl.ks.hex.supporting.employee;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.common.model.WorkHours;

class EmployeeTest {
    private final EmployeeConfiguration employeeConfiguration = new EmployeeConfiguration(new MockEmployeeRepository());

    private final EmployeeSubService employeeSubService = employeeConfiguration.employeeSubService();
    private final EmployeeService employeeService = employeeConfiguration.employeeService(employeeSubService);

    @Test
    void shouldAcceptNewTimesheet() {
        EmployeeId id = employeeService.createNew(FirstName.of("Krzys"), LastName.of("S"), Money.of(BigDecimal.TEN));
        employeeService.addNewTimesheet(id, WorkHours.of(100));
    }

    @Test
    void shouldNotAcceptSecondTimesheetWithoutInvoice() {
        EmployeeId id = employeeService.createNew(FirstName.of("Krzys"), LastName.of("S"), Money.of(BigDecimal.TEN));
        employeeService.addNewTimesheet(id, WorkHours.of(100));
        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addNewTimesheet(id, WorkHours.of(100));
        });
    }

    @Test
    void shouldAcceptInvoiceToTheTimesheet() {
        EmployeeId id = employeeService.createNew(FirstName.of("Krzys"), LastName.of("S"), Money.of(BigDecimal.TEN));
        employeeService.addNewTimesheet(id, WorkHours.of(100));
        employeeService.addNewInvoice(id, Money.of(BigDecimal.valueOf(1000L)));
    }

    @Test
    void shouldAcceptSecondTimesheetAfterInvoice() {
        EmployeeId id = employeeService.createNew(FirstName.of("Krzys"), LastName.of("S"), Money.of(BigDecimal.TEN));
        employeeService.addNewTimesheet(id, WorkHours.of(100));
        employeeService.addNewInvoice(id, Money.of(BigDecimal.valueOf(1000L)));
        employeeService.addNewTimesheet(id, WorkHours.of(100));
    }

    @Test
    void shouldNotAcceptInvoiceWithWrongAmount() {
        EmployeeId id = employeeService.createNew(FirstName.of("Krzys"), LastName.of("S"), Money.of(BigDecimal.TEN));
        employeeService.addNewTimesheet(id, WorkHours.of(100));
        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addNewInvoice(id, Money.of(BigDecimal.valueOf(1001L)));
        });
    }
}

