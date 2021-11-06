package by.varyvoda.matvey.server.http;

import by.varyvoda.matvey.common.http.entity.HttpRequest;
import by.varyvoda.matvey.common.http.entity.HttpResponse;
import by.varyvoda.matvey.common.http.entity.specification.HttpResponseCode;
import by.varyvoda.matvey.common.http.entity.specification.exception.HttpRequestException;
import by.varyvoda.matvey.server.connection.Connection;
import by.varyvoda.matvey.server.connection.IConnectionHandler;

import java.io.IOException;

public class HttpConnectionHandler implements IConnectionHandler {

    private final HttpRequestDispatcher httpRequestDispatcher = new HttpRequestDispatcher();

    @Override
    public void handle(Connection connection) throws IOException {
        try {
            connection.write(
                    httpRequestDispatcher.dispatch(
                            new HttpRequest(connection.readAll())
                    ).toString()
            );
        } catch (HttpRequestException e) {
            connection.write(HttpResponse.create().code(e.responseCode()).toString());
        } catch (Exception e) {
            connection.write(HttpResponse.create().code(HttpResponseCode.INTERNAL_SERVER_ERROR).toString());
        }
    }
}
