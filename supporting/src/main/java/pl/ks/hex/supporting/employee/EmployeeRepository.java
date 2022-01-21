package pl.ks.hex.supporting.employee;

interface EmployeeRepository {
    Employee getById(EmployeeId employeeId);

    <S extends Employee> S save(S employee);
}
