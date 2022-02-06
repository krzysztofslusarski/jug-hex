package pl.ks.hex.supporting.employee;

import java.util.List;
import java.util.Optional;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;

public interface EmployeeQueryRepository {
    List<EmployeeDto> findAll();

    EmployeeDto getById(EmployeeId employeeId);

    Optional<EmployeeDto> findByFirstNameAndLastName(FirstName firstName, LastName lastName);
}
