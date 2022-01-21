package pl.ks.hex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import pl.ks.hex.common.command.Command;
import pl.ks.hex.common.command.CommandHandler;
import pl.ks.hex.common.command.CommandHandlerExecutor;

@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
class SynchronousCommandHandlerExecutor implements CommandHandlerExecutor {
    private final Map<Class<? extends Command>, CommandHandler> handlerMap;

    SynchronousCommandHandlerExecutor(List<CommandHandler> commandHandlers) {
        if (commandHandlers == null) {
            log.warn("There are no command handlers in application context");
            this.handlerMap = new HashMap<>();
            return;
        }
        commandHandlers.forEach(commandHandler -> {
            log.info("Adding support for command with class: {}", commandHandler.handlingCommandClass().getSimpleName());
        });
        this.handlerMap = commandHandlers.stream()
                .collect(Collectors.toMap(CommandHandler::handlingCommandClass, Function.identity()));
    }

    @Override
    public void execute(Command command) {
        CommandHandler handler = handlerMap.get(command.getClass());
        String commandClass = command.getClass().getSimpleName();

        log.info("Executing command with class: {}", commandClass);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CommandHandlingStatus commandHandlingStatus = null;
        try {
            handler.handle(command);
            commandHandlingStatus = CommandHandlingStatus.SUCCESS;
        } catch (RuntimeException e) {
            commandHandlingStatus = CommandHandlingStatus.FAIL;
            throw e;
        } finally {
            stopWatch.stop();
            log.info("Command with class: {} executed in {}ms with result {}", commandClass, stopWatch.getTotalTimeMillis(), commandHandlingStatus);
        }
    }
}
