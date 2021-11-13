package by.varyvoda.matvey.common.http.entity;

import by.varyvoda.matvey.common.http.entity.specification.HttpMethod;
import by.varyvoda.matvey.common.http.entity.specification.HttpVersion;
import by.varyvoda.matvey.common.http.entity.specification.exception.HttpException;
import by.varyvoda.matvey.common.http.entity.specification.exception.request.BadRequest;
import by.varyvoda.matvey.common.http.entity.specification.exception.request.HttpRequestException;
import by.varyvoda.matvey.common.http.entity.specification.exception.request.HttpVersionNotSupported;
import by.varyvoda.matvey.common.http.entity.specification.exception.request.MethodNotAllowed;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static by.varyvoda.matvey.common.http.entity.specification.Specification.*;

@Getter
public class HttpRequest extends HttpEntity {

    private Map<String, String> urlParams;
    private HttpMethod method;
    private String url;

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

    private HttpRequest() {
        super();
        urlParams = new LinkedHashMap<>();
        method = HttpMethod.GET;
        url = "/";
    }

    public HttpRequest(String template) throws HttpException {
        super(template);
    }

    @Override
    protected void scanFirstLine(String firstLine) throws HttpRequestException {
        String[] tokens = firstLine.split(" +", 3);

        if (tokens.length != 3)
            throw new BadRequest("First line is incorrect.");

        method = checkMethod(tokens[0]);
        url = parseUrl(tokens[1]);
        urlParams = new LinkedHashMap<>(parseParams(tokens[1]));
        version = checkVersion(tokens[2]);
    }

    private Map<String, String> parseParams(String url) {
        if (!url.contains(QUERY_PARAM_SECTION_SEPARATOR)) return Map.of();
        return Arrays.stream(
                        url.substring(
                                url.indexOf(QUERY_PARAM_SECTION_SEPARATOR) + QUERY_PARAM_SECTION_SEPARATOR.length()
                        ).split(QUERY_PARAM_SEPARATOR))
                .collect(Collectors.toMap(
                        param -> param.substring(0, param.indexOf(QUERY_PARAM_KEY_VALUE_SEPARATOR)),
                        param -> param.substring(param.indexOf(QUERY_PARAM_KEY_VALUE_SEPARATOR) + QUERY_PARAM_KEY_VALUE_SEPARATOR.length())
                ));
    }

    private String parseUrl(String url) {
        int queryParamSectionStartIndex = url.indexOf(QUERY_PARAM_SECTION_SEPARATOR);
        return queryParamSectionStartIndex == -1 ? url : url.substring(0, queryParamSectionStartIndex);
    }

    private HttpMethod checkMethod(String token) throws MethodNotAllowed {
        HttpMethod method = Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name().equals(token))
                .findFirst().orElse(null);
        if (method != null)
            return method;

        throw new MethodNotAllowed("Such method not allowed: " + token);
    }

    private HttpVersion checkVersion(String token) throws HttpVersionNotSupported {
        HttpVersion version = Arrays.stream(HttpVersion.values())
                .filter(httpVersion -> httpVersion.getLabel().equals(token))
                .findFirst().orElse(null);
        if (version != null)
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

    public HttpRequest addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpRequest addParam(String key, String value) {
        urlParams.put(key, value);
        return this;
    }

    public HttpRequest setHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public HttpRequest body(Object body) {
        this.body = mapper.toJson(body);
        return this;
    }

    @Override
    public String toString() {
        return (method.name() + " "
                + url + formUrlParams() + " "
                + version.getLabel() + "\n"
                + super.toString()).trim();
    }

    private String formUrlParams() {
        return urlParams.size() == 0 ? ""
                : QUERY_PARAM_SECTION_SEPARATOR + urlParams.entrySet().stream()
                .map(entry -> entry.getKey() + QUERY_PARAM_KEY_VALUE_SEPARATOR + entry.getValue())
                .reduce("", (accumulator, value) -> accumulator + value + QUERY_PARAM_SEPARATOR)
                .replaceAll(QUERY_PARAM_SEPARATOR + "$", "");
    }
}
