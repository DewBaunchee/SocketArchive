package by.varyvoda.matvey.common.http.entity.specification.exception;

import by.varyvoda.matvey.common.http.entity.specification.HttpResponseCode;

public class MethodNotAllowed extends HttpRequestException {

    public MethodNotAllowed(String message) {
        super(message);
    }

    @Override
    public HttpResponseCode responseCode() {
        return HttpResponseCode.METHOD_NOT_ALLOWED;
    }
}
