package by.varyvoda.matvey.common.http.entity;

import by.varyvoda.matvey.common.http.entity.specification.exception.BadRequest;
import by.varyvoda.matvey.common.http.entity.specification.exception.HttpRequestException;
import by.varyvoda.matvey.common.http.entity.specification.exception.HttpVersionNotSupported;
import by.varyvoda.matvey.common.http.entity.specification.exception.MethodNotAllowed;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static by.varyvoda.matvey.common.http.entity.specification.Specification.HEADER_KEY_VALUE_SEPARATOR;

@Getter
public abstract class HttpEntity {

    protected Map<String, String> headers  = new LinkedHashMap<>();
    protected String body = "";

    protected HttpEntity() {}

    public HttpEntity(String template) throws HttpRequestException {
        Scanner scanner = new Scanner(template);
        scanFirstLine(scanner.nextLine());
        headers.putAll(scanHeaders(scanner));
        body = scanBody(scanner);
    }

    private Map<String, String> scanHeaders(Scanner template) throws BadRequest {
        Map<String, String> headers = new LinkedHashMap<>();
        String line;
        while(template.hasNextLine() && !(line = template.nextLine()).isBlank()) {
            int separatorPosition = line.indexOf(HEADER_KEY_VALUE_SEPARATOR);
            if(separatorPosition == -1)
                throw new BadRequest("No header key value separator (" + HEADER_KEY_VALUE_SEPARATOR + ")");

            headers.put(
                    line.substring(0, separatorPosition),
                    line.substring(separatorPosition + HEADER_KEY_VALUE_SEPARATOR.length())
            );
        }
        return headers;
    }

    private String scanBody(Scanner template) {
        StringBuilder bodyBuilder = new StringBuilder(); // bodyBuilder lol
        while(template.hasNextLine()) bodyBuilder.append(template.nextLine());
        return bodyBuilder.toString();
    }

    @Override
    public String toString() {
        return (headers.entrySet().stream()
                .map(entry -> entry.getKey() + HEADER_KEY_VALUE_SEPARATOR + entry.getValue())
                .reduce("", (accumulator, header) -> accumulator + header + "\n")
                + "\n"
                + body).trim();
    }

    protected abstract void scanFirstLine(String firstLine) throws HttpRequestException;
}
