package pl.ks.hex.supporting.employee;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EmployeeQueryRepositoryImpl implements EmployeeQueryRepository {
    private final EmployeePrivateRepository employeePrivateRepository;

    @Override
    public List<EmployeeDto> findAll() {
        return employeePrivateRepository.findAll().stream()
                .map(this::map)
                .toList();
    }

    @Override
    public EmployeeDto getById(EmployeeId employeeId) {
        return map(employeePrivateRepository.getById(employeeId));
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
