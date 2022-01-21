package pl.ks.hex.employee;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findById(EmployeeId employeeId);

    void save(Employee employee);
}
