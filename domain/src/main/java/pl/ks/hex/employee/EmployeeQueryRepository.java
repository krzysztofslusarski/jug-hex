package pl.ks.hex.employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeQueryRepository {
    Optional<EmployeeDto> findById(EmployeeId employeeId);

    List<EmployeeDto> findAll();
}
