package by.varyvoda.matvey.server.connection_listener;

import by.varyvoda.matvey.server.connection.Connection;
import by.varyvoda.matvey.server.connection.IConnectionHandler;
import lombok.RequiredArgsConstructor;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
public class Dispatcher implements Closeable {

    private final Queue<Connection> connections = new ConcurrentLinkedQueue<>();
    private final IConnectionHandler connectionHandler;

    public void dispatch(Socket socket) {
        Thread handlingThread = new Thread(() -> {
            try (Connection connection = new Connection(socket)) {
                connections.add(connection);
                connectionHandler.handle(connection);
            } catch (Exception e) {
                connections.removeIf((connection -> connection.getConnectedSocket().equals(socket)));
            }
        });
        handlingThread.start();
    }

    public void close() throws IOException {
        for (Connection connection : connections) connection.close();
    }
}
