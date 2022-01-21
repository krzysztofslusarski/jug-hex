package pl.ks.hex.supporting.employee;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EmployeeSubServiceImpl implements EmployeeSubService {
    private final EmployeeRepository employeeRepository;
}
