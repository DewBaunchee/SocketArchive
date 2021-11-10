package by.varyvoda.matvey.server.http;

import by.varyvoda.matvey.common.command_line.CommandLine;
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
        HttpRequestConnection httpRequestConnection = new HttpRequestConnection(connection);
        try {
            httpRequestConnection.write(
                    httpRequestDispatcher.dispatch(
                            httpRequestConnection.readAll()
                    )
            );
        } catch (HttpRequestException e) {
            httpRequestConnection.write(HttpResponse.create().code(e.responseCode()));
            CommandLine.println("Error during request.");
            CommandLine.printStackTrace(e);
        } catch (Exception e) {
            httpRequestConnection.write(HttpResponse.create().code(HttpResponseCode.INTERNAL_SERVER_ERROR));
            CommandLine.println("Error during request.");
            CommandLine.printStackTrace(e);
        }
        httpRequestConnection.shutdownOutput();
    }
}
