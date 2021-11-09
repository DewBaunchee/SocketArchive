package by.varyvoda.matvey.common.http.entity.connection;

import by.varyvoda.matvey.common.connection.Connection;
import by.varyvoda.matvey.common.connection.IConnection;
import by.varyvoda.matvey.common.http.entity.HttpRequest;
import by.varyvoda.matvey.common.http.entity.HttpResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class HttpResponseConnection implements IConnection<HttpRequest, HttpResponse> {

    private final Connection connection;

    @Override
    public void write(byte[] writable) throws IOException {
        connection.write(writable);
    }

    @Override
    public HttpResponse readAll() throws IOException {
        return new HttpResponse(connection.readAll());
    }

    @Override
    public void write(HttpRequest writable) throws IOException {
        connection.write(writable.toString());
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }
}
