package by.varyvoda.matvey.server.http;

import by.varyvoda.matvey.common.http.entity.HttpRequest;
import by.varyvoda.matvey.common.http.entity.HttpResponse;
import by.varyvoda.matvey.common.http.entity.specification.HttpMethod;
import by.varyvoda.matvey.common.http.entity.specification.exception.NotImplemented;
import by.varyvoda.matvey.server.handler.HttpRequestHandler;
import by.varyvoda.matvey.server.http.end_point.EndPoint;
import by.varyvoda.matvey.server.http.end_point.scanner.HttpEndPointScanner;

import java.util.Map;

public class HttpRequestDispatcher {

    private final Map<HttpMethod, EndPoint<HttpRequest, HttpResponse>> endPoints;
    
    public HttpRequestDispatcher() {
        endPoints = HttpEndPointScanner.findIn(new HttpRequestHandler());
    }

    public HttpResponse dispatch(HttpRequest httpRequest) throws Exception {
        EndPoint<HttpRequest, HttpResponse> endPoint = endPoints.get(httpRequest.getMethod());
        if(endPoint == null)
            throw new NotImplemented(httpRequest.getMethod() + " method not implemented yet.");
        return endPoint.invoke(httpRequest);
    }
}
