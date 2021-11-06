package by.varyvoda.matvey.common.http.entity;

import by.varyvoda.matvey.common.http.entity.specification.HttpMethod;
import by.varyvoda.matvey.common.http.entity.specification.HttpVersion;
import by.varyvoda.matvey.common.http.entity.specification.exception.BadRequest;
import by.varyvoda.matvey.common.http.entity.specification.exception.HttpRequestException;
import by.varyvoda.matvey.common.http.entity.specification.exception.HttpVersionNotSupported;
import by.varyvoda.matvey.common.http.entity.specification.exception.MethodNotAllowed;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class HttpRequest extends HttpEntity {

    private HttpMethod method = HttpMethod.GET;
    private String url = "/";
    private HttpVersion version = HttpVersion.HTTP_1_1;

    public static HttpRequest get() {
        return create().method(HttpMethod.GET);
    }

    public static HttpRequest post() {
        return create().method(HttpMethod.POST);
    }

    public static HttpRequest put() {
        return create().method(HttpMethod.PUT);
    }

    public static HttpRequest create() {
        return new HttpRequest();
    }

    private HttpRequest() {}

    public HttpRequest(String template) throws HttpRequestException {
        super(template);
    }

    @Override
    protected void scanFirstLine(String firstLine) throws HttpRequestException {
        String[] tokens = firstLine.split(" +");

        if(tokens.length != 3)
            throw new BadRequest("First line is incorrect.");

        method = checkMethod(tokens[0]);
        url = tokens[1];
        version = checkVersion(tokens[2]);
    }

    private HttpMethod checkMethod(String token) throws MethodNotAllowed {
        HttpMethod method = Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name().equals(token))
                .findFirst().orElse(null);
        if(method != null)
            return method;

        throw new MethodNotAllowed("Such method not allowed: " + token);
    }

    private HttpVersion checkVersion(String token) throws HttpVersionNotSupported {
        HttpVersion version = Arrays.stream(HttpVersion.values())
                .filter(httpVersion -> httpVersion.getLabel().equals(token))
                .findFirst().orElse(null);
        if(version != null)
            return version;

        throw new HttpVersionNotSupported("Such version not supported: " + token);
    }

    public HttpRequest method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public HttpRequest url(String url) {
        this.url = url;
        return this;
    }

    public HttpRequest version(HttpVersion version) {
        this.version = version;
        return this;
    }

    public HttpRequest addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpRequest body(String body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return (method.name() + " "
                + url + " "
                + version.getLabel() + "\n"
                + super.toString()).trim();
    }
}
