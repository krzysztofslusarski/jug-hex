package pl.ks.hex.employee;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EmployeeQueryRepositoryImpl implements EmployeeQueryRepository {
    private final EmployeeRepository employeeRepository;

    @Override
    public Optional<EmployeeDto> findById(EmployeeId employeeId) {
        return employeeRepository.findById(employeeId).map(this::map);
    }

    @Override
    public List<EmployeeDto> findAll() {
        return null;
    }

    private EmployeeDto map(Employee employee) {
        return EmployeeDto.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .id(employee.getId())
                .build();
    }
}
