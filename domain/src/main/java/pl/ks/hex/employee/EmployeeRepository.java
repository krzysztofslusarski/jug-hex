package pl.ks.hex.employee;

import java.util.List;

public interface EmployeeRepository {
    Employee getById(EmployeeId employeeId);

    List<Employee> findAll();

    void save(Employee employee);
}
