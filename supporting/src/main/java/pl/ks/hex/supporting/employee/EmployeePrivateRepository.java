package pl.ks.hex.supporting.employee;

import java.util.List;

interface EmployeePrivateRepository extends EmployeeRepository {
    Employee getById(EmployeeId employeeId);

    <S extends Employee> S save(S employee);

    List<Employee> findAll();
}
