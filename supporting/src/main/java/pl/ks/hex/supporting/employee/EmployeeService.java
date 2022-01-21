package pl.ks.hex.supporting.employee;

import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.common.model.WorkHours;

public interface EmployeeService {
    EmployeeId createNew(FirstName firstName, LastName lastName, Money hourlyEarnings);

    void addNewTimesheet(EmployeeId employeeId, WorkHours hours);

    void settleNewInvoice(EmployeeId employeeId, Money payment);
}
