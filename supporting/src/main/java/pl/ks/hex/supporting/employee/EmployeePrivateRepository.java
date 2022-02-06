package pl.ks.hex.supporting.employee;

import java.util.List;
import java.util.Optional;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;

interface EmployeePrivateRepository extends EmployeeRepository {
    Employee getById(EmployeeId employeeId);

    <S extends Employee> S save(S employee);

    Optional<Employee> findByFirstNameAndLastName(FirstName firstName, LastName lastName);

    List<Employee> findAll();
}
