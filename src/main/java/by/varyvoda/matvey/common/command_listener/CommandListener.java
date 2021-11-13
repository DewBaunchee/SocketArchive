package by.varyvoda.matvey.common.command_listener;

import by.varyvoda.matvey.common.command_line.CommandLine;
import by.varyvoda.matvey.common.command_listener.exception.CannotRegisterCommandException;

import java.io.Closeable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandListener extends Thread implements Closeable {

    private static final Command NOT_FOUND_COMMAND = Command.builder()
            .runnable(args -> CommandLine.println("Command not found."))
            .build();
    private final Map<String, Command> commands = new LinkedHashMap<>();

    public void startSync() {
        run();
    }

    @Override
    public void run() {
        CommandLine.println(buildCommandList() + "************************************");
        CommandLine.setInputMessage(">>> Print command >>> ");
        while (!isInterrupted()) {
            String line = CommandLine.readLine().trim();
            String[] tokens = line.split(" +");
            String cmd = tokens[0];
            String[] args = tokens.length == 1 ? new String[]{}
                    : Arrays.copyOfRange(tokens, 1, tokens.length);

            try {
                commands.getOrDefault(cmd, NOT_FOUND_COMMAND).run(args);
            } catch (Exception e) {
                CommandLine.printStackTrace(e);
            }
        }
    }

    public void registerCommand(Command cmd) {
        if (isAlive()) throw new CannotRegisterCommandException("Listener is already launched.");
        commands.put(cmd.getCommand(), cmd);
    }

    private String buildCommandList() {
        return commands.values().stream()
                .map(command -> String.format("\t- %s\n", command))
                .reduce("Commands:\n", (accumulator, command) -> accumulator + command);
    }

    @Override
    public void close() {
        interrupt();
    }
}
