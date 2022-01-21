package pl.ks.hex.employee;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;

@Slf4j
@RequiredArgsConstructor
class EmployeeFactoryImpl implements EmployeeFactory {
    @Override
    public Employee createNew(FirstName firstName, LastName lastName, Money hourlyRate) {
        EmployeeId newId = EmployeeId.of(UUID.randomUUID());
        return Employee.builder()
                .id(newId)
                .firstName(firstName)
                .lastName(lastName)
                .hourlyRate(hourlyRate)
                .build();
    }
}
