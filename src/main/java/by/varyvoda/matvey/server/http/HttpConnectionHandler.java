package by.varyvoda.matvey.server.http;

import by.varyvoda.matvey.common.http.entity.HttpResponse;
import by.varyvoda.matvey.common.http.entity.connection.HttpRequestConnection;
import by.varyvoda.matvey.common.http.entity.specification.HttpResponseCode;
import by.varyvoda.matvey.common.http.entity.specification.exception.request.HttpRequestException;
import by.varyvoda.matvey.common.connection.Connection;
import by.varyvoda.matvey.common.connection.IConnectionHandler;

import java.io.IOException;

public class HttpConnectionHandler implements IConnectionHandler {

    private final HttpRequestDispatcher httpRequestDispatcher = new HttpRequestDispatcher();

    @Override
    public void handle(Connection connection) throws IOException {
        try {
            HttpRequestConnection httpRequestConnection = new HttpRequestConnection(connection);
            httpRequestConnection.write(
                    httpRequestDispatcher.dispatch(
                            httpRequestConnection.readAll()
                    )
            );
        } catch (HttpRequestException e) {
            connection.write(HttpResponse.create().code(e.responseCode()).toString());
        } catch (Exception e) {
            connection.write(HttpResponse.create().code(HttpResponseCode.INTERNAL_SERVER_ERROR).toString());
        }
    }
}
