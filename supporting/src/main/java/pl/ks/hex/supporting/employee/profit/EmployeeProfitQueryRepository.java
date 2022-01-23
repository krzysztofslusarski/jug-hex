package pl.ks.hex.supporting.employee.profit;

import java.util.List;

public interface EmployeeProfitQueryRepository {
    List<EmployeeProfit> findAll();
}
