package by.varyvoda.matvey.client;

import by.varyvoda.matvey.client.service.FileService;
import by.varyvoda.matvey.common.command_line.CommandLine;
import by.varyvoda.matvey.common.command_listener.Command;
import by.varyvoda.matvey.common.command_listener.CommandListener;

public class ClientMain {

    private static final FileService fileService = new FileService();

    public static void main(String[] args) {
        CommandListener commandListener = new CommandListener();
        commandListener.registerCommand(
                Command.builder()
                        .command("getByName")
                        .requiredArgsCount(1)
                        .runnable(
                                arguments -> fileService.getByName(arguments[0])
                                        .subscribe(
                                                value -> CommandLine.println(value + ""),
                                                error -> CommandLine.println(error.getMessage())
                                        )
                        )
                        .description("Get student files by name")
                        .build()
        );

        commandListener.registerCommand(
                Command.builder()
                        .command("exit")
                        .runnable(arguments -> commandListener.close())
                        .description("Exit")
                        .build()
        );

        commandListener.startSync();
    }
}
