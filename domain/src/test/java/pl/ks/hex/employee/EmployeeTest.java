package pl.ks.hex.employee;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.common.model.WorkHours;

class EmployeeTest {
    @Test
    void shouldAcceptNewTimesheet() {
        Employee employee = new Employee(FirstName.of("Krzysztof"), LastName.of("Ślusarski"), Money.of(BigDecimal.TEN));
        employee.addNewTimesheet(WorkHours.of(100));
    }

    @Test
    void shouldNotAcceptSecondTimesheetWithoutInvoice() {
        Employee employee = new Employee(FirstName.of("Krzysztof"), LastName.of("Ślusarski"), Money.of(BigDecimal.TEN));
        employee.addNewTimesheet(WorkHours.of(100));
        assertThrows(IllegalArgumentException.class, () -> {
            employee.addNewTimesheet(WorkHours.of(100));
        });
    }

    @Test
    void shouldAcceptInvoiceToTheTimesheet() {
        Employee employee = new Employee(FirstName.of("Krzysztof"), LastName.of("Ślusarski"), Money.of(BigDecimal.TEN));
        employee.addNewTimesheet(WorkHours.of(100));
        employee.addNewInvoice(Money.of(BigDecimal.valueOf(1000L)));
    }

    @Test
    void shouldAcceptSecondTimesheetAfterInvoice() {
        Employee employee = new Employee(FirstName.of("Krzysztof"), LastName.of("Ślusarski"), Money.of(BigDecimal.TEN));
        employee.addNewTimesheet(WorkHours.of(100));
        employee.addNewInvoice(Money.of(BigDecimal.valueOf(1000L)));
        employee.addNewTimesheet(WorkHours.of(100));
    }

    @Test
    void shouldNotAcceptInvoiceWithWrongAmount() {
        Employee employee = new Employee(FirstName.of("Krzysztof"), LastName.of("Ślusarski"), Money.of(BigDecimal.TEN));
        employee.addNewTimesheet(WorkHours.of(100));
        assertThrows(IllegalArgumentException.class, () -> {
            employee.addNewInvoice(Money.of(BigDecimal.valueOf(1001L)));
        });
    }
}

