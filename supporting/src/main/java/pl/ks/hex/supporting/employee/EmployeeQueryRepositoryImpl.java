package pl.ks.hex.supporting.employee;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EmployeeQueryRepositoryImpl implements EmployeeQueryRepository {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll().stream()
                .map(this::map)
                .toList();
    }

    @Override
    public EmployeeDto getById(EmployeeId employeeId) {
        return map(employeeRepository.getById(employeeId));
    }

    private EmployeeDto map(Employee employee) {
        return EmployeeDto.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .hourlyEarnings(employee.getHourlyEarnings())
                .id(employee.getId())
                .lastNotSettledTimesheetWorkTime(employee.getLastNotSettledTimesheetWorkTime())
                .build();
    }
}
