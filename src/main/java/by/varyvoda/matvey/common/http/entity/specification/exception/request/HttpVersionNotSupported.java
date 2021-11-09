package by.varyvoda.matvey.common.http.entity.specification.exception.request;

import by.varyvoda.matvey.common.http.entity.specification.HttpResponseCode;
import by.varyvoda.matvey.common.http.entity.specification.exception.request.HttpRequestException;

public class HttpVersionNotSupported extends HttpRequestException {

    public HttpVersionNotSupported(String message) {
        super(message);
    }

    @Override
    public HttpResponseCode responseCode() {
        return HttpResponseCode.HTTP_VERSION_NOT_SUPPORTED;
    }
}
