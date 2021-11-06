package by.varyvoda.matvey.common.http.entity;

import by.varyvoda.matvey.common.http.entity.specification.HttpResponseCode;
import by.varyvoda.matvey.common.http.entity.specification.HttpVersion;
import by.varyvoda.matvey.common.http.entity.specification.exception.BadRequest;
import by.varyvoda.matvey.common.http.entity.specification.exception.HttpRequestException;
import by.varyvoda.matvey.common.http.entity.specification.exception.HttpVersionNotSupported;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class HttpResponse extends HttpEntity {

    private HttpVersion version = HttpVersion.HTTP_1_1;
    private int code = HttpResponseCode.OK.getCode();
    private String message = HttpResponseCode.OK.getMessage();

    public static HttpResponse ok() {
        return create().code(HttpResponseCode.OK);
    }

    public static HttpResponse create() {
        return new HttpResponse();
    }

    private HttpResponse() {
        super();
    }

    public HttpResponse(String template) throws HttpRequestException {
        super(template);
    }

    @Override
    protected void scanFirstLine(String firstLine) throws BadRequest, HttpVersionNotSupported {
        String[] tokens = firstLine.split(" +");

        if (tokens.length != 3)
            throw new BadRequest("First line is incorrect.");

        version = checkVersion(tokens[0]);
        code = Integer.parseInt(tokens[1]);
        message = tokens[2];
    }

    private HttpVersion checkVersion(String token) throws HttpVersionNotSupported {
        HttpVersion version = Arrays.stream(HttpVersion.values())
                .filter(httpVersion -> httpVersion.getLabel().equals(token))
                .findFirst().orElse(null);
        if (version != null)
            return version;

        throw new HttpVersionNotSupported("Such version not supported: " + token);
    }

    public HttpResponse version(HttpVersion version) {
        this.version = version;
        return this;
    }

    public HttpResponse code(int code) {
        this.code = code;
        return this;
    }

    public HttpResponse code(HttpResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
        return this;
    }

    public HttpResponse message(String message) {
        this.message = message;
        return this;
    }

    public HttpResponse addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpResponse body(String body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return (version.getLabel() + " "
                + code + " "
                + message + "\n"
                + super.toString()).trim();
    }
}