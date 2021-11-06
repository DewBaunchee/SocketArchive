package by.varyvoda.matvey.server.command_listener;

import by.varyvoda.matvey.common.command_line.CommandLine;
import by.varyvoda.matvey.server.command_listener.exception.CannotRegisterCommandException;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandListener extends Thread implements Closeable {

    private static final Command NOT_FOUND_COMMAND = Command.builder()
            .runnable(() -> CommandLine.println("Command not found."))
            .build();
    private final Map<String, Command> commands = new LinkedHashMap<>();

    @Override
    public void run() {
        CommandLine.println(buildCommandList() + "************************************");
        CommandLine.setInputMessage(">>> Print command >>> ");
        while(!isInterrupted()) {
            try {
                commands.getOrDefault(CommandLine.readLine(), NOT_FOUND_COMMAND).run();
            } catch (IOException e) {
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
                .map(command -> String.format("\t- \"%s\" to %s\n", command.getCommand(), command.getDescription()))
                .reduce("Commands:\n", (accumulator, command) -> accumulator + command);
    }

    @Override
    public void close() {
        interrupt();
    }
}
