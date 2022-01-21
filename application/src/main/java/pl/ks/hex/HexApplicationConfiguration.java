package pl.ks.hex;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.ks.hex.common.command.CommandHandler;
import pl.ks.hex.common.command.CommandHandlerExecutor;

@Configuration(proxyBeanMethods = false)
class HexApplicationConfiguration {
    @Bean
    CommandHandlerExecutor commandHandlerExecutor(@Autowired(required = false) List<CommandHandler> commandHandlers) {
        return new SynchronousCommandHandlerExecutor(commandHandlers);
    }
}
