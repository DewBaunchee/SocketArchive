package by.varyvoda.matvey.server.connection_listener;

import by.varyvoda.matvey.server.http.HttpConnectionHandler;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.ServerSocket;

@RequiredArgsConstructor
public class Server extends Thread {

    private final int port;

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port); Dispatcher dispatcher = new Dispatcher(new HttpConnectionHandler())) {
            while(!isInterrupted()) {
                dispatcher.dispatch(serverSocket.accept());
            }
        } catch (IOException e) {
            shutdown();
            throw new RuntimeException("Port " + port + " is already listening. Free it and restart server.");
        }
    }

    public void shutdown() {
        interrupt();
    }
}
