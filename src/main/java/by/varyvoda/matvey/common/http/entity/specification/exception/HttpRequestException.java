package by.varyvoda.matvey.common.http.entity.specification.exception;

import by.varyvoda.matvey.common.http.entity.specification.HttpResponseCode;

public abstract class HttpRequestException extends Exception {

    public HttpRequestException(String message) {
        super(message);
    }

    public abstract HttpResponseCode responseCode();
}
