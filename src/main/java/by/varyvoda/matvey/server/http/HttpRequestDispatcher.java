package by.varyvoda.matvey.server.http;

import by.varyvoda.matvey.common.command_line.CommandLine;
import by.varyvoda.matvey.common.http.entity.HttpRequest;
import by.varyvoda.matvey.common.http.entity.HttpResponse;
import by.varyvoda.matvey.common.http.entity.specification.exception.request.NotImplemented;
import by.varyvoda.matvey.server.app.ServiceLocator;
import by.varyvoda.matvey.server.app.dao.iface.IStudentFileDao;
import by.varyvoda.matvey.server.app.dao.student_file.IStudentFileService;
import by.varyvoda.matvey.server.app.dao.student_file.StudentFileDao;
import by.varyvoda.matvey.server.app.handler.HttpRequestHandler;
import by.varyvoda.matvey.server.app.service.StudentFileService;
import by.varyvoda.matvey.server.http.end_point.EndPoint;
import by.varyvoda.matvey.server.http.end_point.scanner.HttpEndPointScanner;

import java.util.Map;

public class HttpRequestDispatcher {

    static {
        ServiceLocator.register(IStudentFileDao.class, new StudentFileDao());
        ServiceLocator.register(IStudentFileService.class, new StudentFileService());
    }

    private final Map<String, EndPoint<HttpRequest, HttpResponse>> endPoints;
    
    public HttpRequestDispatcher() {
        endPoints = HttpEndPointScanner.findIn(new HttpRequestHandler());
    }

    public HttpResponse dispatch(HttpRequest httpRequest) throws Exception {
        CommandLine.println(">>>>> Got request >>>>> \n\n" + httpRequest + "\n");
        EndPoint<HttpRequest, HttpResponse> endPoint = endPoints.get(httpRequest.getMethod() + httpRequest.getUrl());

        if(endPoint == null)
            throw new NotImplemented(httpRequest.getMethod() + " method not implemented yet.");

        HttpResponse httpResponse = endPoint.invoke(httpRequest);
        CommandLine.println("<<<<< Result response <<<<< \n\n" + httpResponse + "\n");
        return httpResponse == null ? HttpResponse.nullBody() : httpResponse;
    }
}
