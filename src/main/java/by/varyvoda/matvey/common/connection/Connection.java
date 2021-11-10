package by.varyvoda.matvey.common.connection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Getter
@Setter
@RequiredArgsConstructor
public class Connection implements IConnection<String, String> {

    private final Socket connectedSocket;

    public void write(byte[] writable) throws IOException {
        connectedSocket.getOutputStream().write(writable);
        connectedSocket.getOutputStream().flush();
    }

    public String readAll() throws IOException {
        Scanner requestScanner = new Scanner(connectedSocket.getInputStream());
        StringBuilder stringBuilder = new StringBuilder();
        while(requestScanner.hasNextLine())
            stringBuilder.append(requestScanner.nextLine());
        return stringBuilder.toString();
    }

    public void write(String writable) throws IOException {
        write(writable.getBytes(StandardCharsets.UTF_8));
    }

    public Socket getConnectedSocket() {
        return connectedSocket;
    }

    @Override
    public void shutdownOutput() throws IOException {
        connectedSocket.shutdownOutput();
    }

    @Override
    public void close() throws IOException {
        connectedSocket.close();
    }
}
