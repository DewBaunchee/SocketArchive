package by.varyvoda.matvey.client.http;

import by.varyvoda.matvey.client.config.Config;
import by.varyvoda.matvey.client.observable.Observable;
import by.varyvoda.matvey.common.connection.Connection;
import by.varyvoda.matvey.common.http.entity.HttpRequest;
import by.varyvoda.matvey.common.http.entity.HttpResponse;
import by.varyvoda.matvey.common.http.entity.connection.HttpResponseConnection;
import by.varyvoda.matvey.common.http.entity.specification.exception.HttpException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

@RequiredArgsConstructor
public class HttpClient {

    private final String host;
    private final int port;
    private final Gson gson = new Gson();

    public <R> Observable<R> get(String url, Class<R> resultClass) {
        return get(url, Map.of(), resultClass);
    }

    public <R> Observable<R> get(String url, Map<String, String> headers, Class<R> resultClass) {
        return get(url, headers, "", resultClass);
    }

    public <R> Observable<R> get(String url, Map<String, String> headers, String body, Class<R> resultClass) {
        return send(HttpRequest.get()
                .url(url)
                .setHeaders(headers)
                .body(body), resultClass);
    }

    public void post(String url,  Map<String, String> headers, String body) {
        send(HttpRequest.post()
                .url(url)
                .setHeaders(headers)
                .body(body));
    }

    public void put(String url,  Map<String, String> headers, String body) {
        send(HttpRequest.put()
                .url(url)
                .setHeaders(headers)
                .body(body));
    }

    private void send(HttpRequest request) {
        new Thread(() -> {
            try(Socket socket = new Socket(Config.serverUrl, Config.port)) {

                Connection connection = new Connection(socket);
                connection.write(request.toString());

            } catch (IOException e) {
                System.out.println("Request error: " + e.getMessage());
            }
        }).start();
    }

    private <R> Observable<R> send(HttpRequest request, Class<R> resultClass) {
        Observable<R> result = new Observable<>();
        new Thread(() -> {
            try(Socket socket = new Socket(host, port)) {

                HttpResponseConnection connection = new HttpResponseConnection(new Connection(socket));
                connection.write(request);
                HttpResponse response = connection.readAll();
                result.setValue(gson.fromJson(response.getBody(), resultClass));

            } catch (HttpException e) {
                System.out.println("Http exception: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Request error: " + e.getMessage());
            }
        }).start();
        return result;
    }
}
