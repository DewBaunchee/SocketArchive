package by.varyvoda.matvey.common.http.entity.specification.exception;

import by.varyvoda.matvey.common.http.entity.specification.HttpResponseCode;

public class BadRequest extends HttpRequestException {

    public BadRequest(String message) {
        super(message);
    }

    @Override
    public HttpResponseCode responseCode() {
        return HttpResponseCode.BAD_REQUEST;
    }
}
