package pl.ks.hex.supporting.employee.command;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import pl.ks.hex.common.command.CommandHandlerExecutor;
import pl.ks.hex.common.model.FirstName;
import pl.ks.hex.common.model.LastName;
import pl.ks.hex.common.model.Money;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
class TestIt {
    private final PlatformTransactionManager transactionManager;
    private final CommandHandlerExecutor commandHandlerExecutor;

    @EventListener
    public void test(ApplicationReadyEvent applicationReadyEvent) {
        NewEmployeeCommand command = new NewEmployeeCommand(FirstName.of("A"), LastName.of("B"), Money.of(BigDecimal.TEN));
        new TransactionTemplate(transactionManager).execute(status -> {
           commandHandlerExecutor.execute(command);
           return null;
        });
    }
}
