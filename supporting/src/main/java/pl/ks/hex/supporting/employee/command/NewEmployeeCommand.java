package pl.ks.hex.supporting.employee.command;

import lombok.Builder;
import lombok.Value;
import pl.ks.hex.common.command.Command;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;

@Value
@Builder
public class NewEmployeeCommand implements Command {
    FirstName firstName;
    LastName lastName;
    Money hourlyEarnings;
}
