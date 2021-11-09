package by.varyvoda.matvey.server;

import by.varyvoda.matvey.common.command_listener.Command;
import by.varyvoda.matvey.common.command_listener.CommandListener;
import by.varyvoda.matvey.server.connection_listener.Server;

public class ServerMain {

    public static final int port = 8080;

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server(port);

        CommandListener commandListener = new CommandListener();
        commandListener.registerCommand(
                Command.builder()
                        .command("start")
                        .description("Start server")
                        .runnable(server::start)
                        .build()
        );

        commandListener.registerCommand(
                Command.builder()
                        .command("stop")
                        .description("Stop server")
                        .runnable(server::shutdown)
                        .build()
        );

        commandListener.registerCommand(
                Command.builder()
                        .command("exit")
                        .description("Exit")
                        .runnable(() -> {
                            server.shutdown();
                            commandListener.close();
                        })
                        .build()
        );

        commandListener.start();
        commandListener.join();
    }
}
