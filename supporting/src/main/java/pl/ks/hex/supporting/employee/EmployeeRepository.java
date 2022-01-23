package pl.ks.hex.supporting.employee;

public interface EmployeeRepository {
    Employee getById(EmployeeId employeeId);

    <S extends Employee> S save(S employee);
}
