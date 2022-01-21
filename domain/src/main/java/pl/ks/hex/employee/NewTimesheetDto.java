package pl.ks.hex.employee;

import lombok.Builder;
import lombok.Value;
import pl.ks.hex.common.model.WorkHours;

@Value
@Builder
public class NewTimesheetDto {
    WorkHours workTimeHours;
}
