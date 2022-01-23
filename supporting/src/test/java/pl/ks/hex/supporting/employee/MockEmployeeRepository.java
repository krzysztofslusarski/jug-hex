package pl.ks.hex.supporting.employee;

import pl.ks.hex.common.InMemoryJpaRepository;

class MockEmployeeRepository extends InMemoryJpaRepository<Employee, EmployeeId> implements EmployeePrivateRepository {
    public MockEmployeeRepository() {
        super(Employee::getId);
    }
}
