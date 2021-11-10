package by.varyvoda.matvey.common.command_listener;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class Command {

    private final CommandRunnable runnable;
    private final String command;
    private final Integer requiredArgsCount;
    private final String description;

    @Override
    public int hashCode() {
        return Objects.hash(command);
    }

    public void run(String ...args) {
        if(requiredArgsCount != null && requiredArgsCount != args.length)
            throw new IllegalArgumentException("Required arguments count: " + requiredArgsCount + ".");
        runnable.run(args);
    }

    public interface CommandRunnable {
        void run(String ...args);
    }
}