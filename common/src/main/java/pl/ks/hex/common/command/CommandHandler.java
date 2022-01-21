package pl.ks.hex.common.command;

import java.lang.reflect.ParameterizedType;

public interface CommandHandler<T extends Command> {
    default Class<T> handlingCommandClass() {
        //noinspection unchecked
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    void handle(T command);
}
