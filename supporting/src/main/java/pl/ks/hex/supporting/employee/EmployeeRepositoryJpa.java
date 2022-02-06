package pl.ks.hex.supporting.employee;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;

interface EmployeeRepositoryJpa extends JpaRepository<Employee, EmployeeId>, EmployeePrivateRepository {
    Optional<Employee> findByFirstNameAndLastName(FirstName firstName, LastName lastName);
}
