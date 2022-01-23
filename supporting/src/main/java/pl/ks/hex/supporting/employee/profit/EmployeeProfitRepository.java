package pl.ks.hex.supporting.employee.profit;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ks.hex.employee.EmployeeId;

interface EmployeeProfitRepository extends JpaRepository<EmployeeProfit, EmployeeId>, EmployeeProfitQueryRepository {
}
