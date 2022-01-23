package pl.ks.hex.supporting.pmo;

import pl.ks.hex.common.model.Money;
import pl.ks.hex.employee.EmployeeId;

public interface PmoService {
    Money calculateProfit(EmployeeId employeeId, Money invoicePremium);
}
