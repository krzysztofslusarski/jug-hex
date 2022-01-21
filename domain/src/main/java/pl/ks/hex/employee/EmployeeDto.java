package pl.ks.hex.employee;

import lombok.Builder;
import lombok.Value;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;

@Value
@Builder
public class EmployeeDto {
    EmployeeId id;
    FirstName firstName;
    LastName lastName;
}