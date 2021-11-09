package by.varyvoda.matvey.common.command_listener;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class Command implements Runnable {

    private final Runnable runnable;
    private final String command;
    private final String description;

    @Override
    public int hashCode() {
        return Objects.hash(command);
    }

    public void run() {
        runnable.run();
    }
}
