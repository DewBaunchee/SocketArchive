package by.varyvoda.matvey.client.http;

import by.varyvoda.matvey.client.config.Config;
import by.varyvoda.matvey.client.observable.Observable;
import by.varyvoda.matvey.common.command_line.CommandLine;
import by.varyvoda.matvey.common.connection.Connection;
import by.varyvoda.matvey.common.http.entity.HttpRequest;
import by.varyvoda.matvey.common.http.entity.HttpResponse;
import by.varyvoda.matvey.common.http.entity.connection.HttpResponseConnection;
import by.varyvoda.matvey.common.http.entity.specification.HttpResponseCode;
import by.varyvoda.matvey.common.http.entity.specification.exception.HttpException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;

@RequiredArgsConstructor
public class HttpClient {

    private final String host;
    private final int port;

    public void send(HttpRequest request) {
        new Thread(() -> {
            try (Socket socket = new Socket(Config.serverUrl, Config.port)) {

                Connection connection = new Connection(socket);
                connection.write(request.toString());

            } catch (IOException e) {
                CommandLine.println("Request error: " + e.getMessage());
            } catch (Exception e) {
                CommandLine.printStackTrace(e);
            }
        }).start();
    }

    public <R> Observable<R> send(HttpRequest request, Type resultClass) {
        Observable<R> result = new Observable<>();
        new Thread(() -> {
            try (HttpResponseConnection connection = new HttpResponseConnection(new Connection(new Socket(host, port)))) {

                connection.write(request);
                connection.shutdownOutput();
                HttpResponse response = connection.readAll();

                if (response.getCode() != HttpResponseCode.OK.getCode())
                    result.throwError(new Exception("Response is not OK: " + response.getCode() + " " + response.getMessage()));
                else
                    result.setValue(response.getEntity(resultClass));

            } catch (Exception e) {
                CommandLine.printStackTrace(e);
            }
        }).start();
        return result;
    }
}
