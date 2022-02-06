package pl.ks.hex.supporting.employee;

import java.util.Optional;
import pl.ks.hex.common.InMemoryJpaRepository;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;

class MockEmployeeRepository extends InMemoryJpaRepository<Employee, EmployeeId> implements EmployeePrivateRepository {
    public MockEmployeeRepository() {
        super(Employee::getId);
    }

    @Override
    public Optional<Employee> findByFirstNameAndLastName(FirstName firstName, LastName lastName) {
        return database.values().stream()
                .filter(employee -> employee.getFirstName().equals(firstName)
                        && employee.getLastName().equals(lastName))
                .findAny();
    }
}
