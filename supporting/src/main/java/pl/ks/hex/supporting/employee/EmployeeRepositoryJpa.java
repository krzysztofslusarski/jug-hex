package pl.ks.hex.supporting.employee;

import org.springframework.data.jpa.repository.JpaRepository;

interface EmployeeRepositoryJpa extends JpaRepository<Employee, EmployeeId>, EmployeePrivateRepository {
}
