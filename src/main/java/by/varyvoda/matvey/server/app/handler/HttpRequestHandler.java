package by.varyvoda.matvey.server.app.handler;

import by.varyvoda.matvey.common.app.entity.StudentFile;
import by.varyvoda.matvey.common.http.entity.HttpRequest;
import by.varyvoda.matvey.common.http.entity.HttpResponse;
import by.varyvoda.matvey.common.http.entity.specification.HttpMethod;
import by.varyvoda.matvey.server.app.ServiceLocator;
import by.varyvoda.matvey.server.app.dao.student_file.IStudentFileService;
import by.varyvoda.matvey.server.http.end_point.RequestEndPoint;

import java.util.ArrayList;

public class HttpRequestHandler {

    private final IStudentFileService studentFileService = ServiceLocator.locate(IStudentFileService.class);

    @RequestEndPoint(url = "/getByName", method = HttpMethod.GET)
    public HttpResponse getByName(HttpRequest httpRequest) {
        return HttpResponse.ok().body(studentFileService.getByName(httpRequest.getUrlParams().get("name")));
    }

    @RequestEndPoint(url = "/getAll", method = HttpMethod.GET)
    public HttpResponse getAll(HttpRequest httpRequest) {
        return HttpResponse.ok().body(studentFileService.getAll());
    }

    @RequestEndPoint(url = "/", method = HttpMethod.GET)
    public HttpResponse getById(HttpRequest httpRequest) {
        return HttpResponse.ok().body(studentFileService.getById(Integer.valueOf(httpRequest.getUrlParams().get("id"))));
    }

    @RequestEndPoint(url = "/", method = HttpMethod.POST)
    public HttpResponse update(HttpRequest httpRequest) {
        studentFileService.update(httpRequest.getEntity(StudentFile.class));
        return HttpResponse.ok();
    }

    @RequestEndPoint(url = "/", method = HttpMethod.PUT)
    public HttpResponse get(HttpRequest httpRequest) {
        studentFileService.save(httpRequest.getEntity(StudentFile.class));
        return HttpResponse.ok();
    }
}
