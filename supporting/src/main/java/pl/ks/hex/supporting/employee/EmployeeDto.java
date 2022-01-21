package pl.ks.hex.supporting.employee;

import lombok.Builder;
import lombok.Value;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.common.model.WorkHours;

@Value
@Builder
public class EmployeeDto {
    EmployeeId id;
    FirstName firstName;
    LastName lastName;
    Money hourlyEarnings;
    WorkHours lastNotSettledTimesheetWorkTime;
}
