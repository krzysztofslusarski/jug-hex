package pl.ks.hex.employee;

import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;

public interface EmployeeFactory {
    Employee createNew(FirstName firstName, LastName lastName, Money hourlyRate);
}
