package pl.ks.hex.supporting.employee;

import java.util.List;

public interface EmployeeQueryRepository {
    List<EmployeeDto> findAll();

    EmployeeDto getById(EmployeeId employeeId);
}
