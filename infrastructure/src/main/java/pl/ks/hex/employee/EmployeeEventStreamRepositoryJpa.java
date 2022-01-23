package pl.ks.hex.employee;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface EmployeeEventStreamRepositoryJpa extends JpaRepository<EmployeeEventStream, UUID> {
}
