package by.varyvoda.matvey.server.handler;

import by.varyvoda.matvey.common.http.entity.HttpRequest;
import by.varyvoda.matvey.common.http.entity.HttpResponse;
import by.varyvoda.matvey.common.http.entity.specification.HttpMethod;
import by.varyvoda.matvey.server.http.end_point.RequestEndPoint;

public class HttpRequestHandler {

    @RequestEndPoint(method = HttpMethod.GET)
    public HttpResponse get(HttpRequest httpRequest) {
        return null;
    }
}
