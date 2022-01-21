package pl.ks.hex.employee;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.common.model.WorkHours;

class EmployeeTest {
    private static final EmployeeId RANDOM_EMPLOYEE_ID = EmployeeId.of(UUID.randomUUID());
    private static final FirstName RANDOM_FIRSTNAME = FirstName.of(UUID.randomUUID().toString());
    private static final LastName RANDOM_LASTNAME = LastName.of(UUID.randomUUID().toString());

    @Test
    void shouldReportTimesheet() {
        Employee employee = new Employee(RANDOM_EMPLOYEE_ID, RANDOM_FIRSTNAME, RANDOM_LASTNAME, Money.of(BigDecimal.TEN));

        employee.reportTimesheet(new NewTimesheetDto(WorkHours.of(100)));
    }

    @Test
    void shouldNotReportTimesheetWithoutInvoice() {
        Employee employee = new Employee(RANDOM_EMPLOYEE_ID, RANDOM_FIRSTNAME, RANDOM_LASTNAME, Money.of(BigDecimal.TEN));
        employee.reportTimesheet(new NewTimesheetDto(WorkHours.of(100)));

        assertThrows(IllegalArgumentException.class, () -> {
            employee.reportTimesheet(new NewTimesheetDto(WorkHours.of(100)));
        });
    }

    @Test
    void shouldNotSettleInvalidInvoice() {
        Employee employee = new Employee(RANDOM_EMPLOYEE_ID, RANDOM_FIRSTNAME, RANDOM_LASTNAME, Money.of(BigDecimal.TEN));
        employee.reportTimesheet(new NewTimesheetDto(WorkHours.of(100)));

        assertThrows(IllegalArgumentException.class, () -> {
            employee.settleInvoice(new NewInvoiceDto(Money.of(BigDecimal.TEN)));
        });
    }

    @Test
    void shouldSettleValidInvoiceAndReportNewTimesheet() {
        Employee employee = new Employee(RANDOM_EMPLOYEE_ID, RANDOM_FIRSTNAME, RANDOM_LASTNAME, Money.of(BigDecimal.TEN));
        employee.reportTimesheet(new NewTimesheetDto(WorkHours.of(100)));
        employee.settleInvoice(new NewInvoiceDto(Money.of(BigDecimal.TEN).multiply(100)));
        employee.reportTimesheet(new NewTimesheetDto(WorkHours.of(100)));
    }
}
