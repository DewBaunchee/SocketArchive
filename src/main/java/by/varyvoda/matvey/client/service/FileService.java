package by.varyvoda.matvey.client.service;

import by.varyvoda.matvey.client.config.Config;
import by.varyvoda.matvey.client.http.HttpClient;
import by.varyvoda.matvey.client.observable.Observable;
import by.varyvoda.matvey.common.entity.StudentFile;
import by.varyvoda.matvey.common.http.entity.HttpRequest;

import java.lang.reflect.Array;
import java.util.List;

public class FileService implements IFileService {

    private final HttpClient http = new HttpClient(Config.serverUrl, Config.port);

    @Override
    public Observable<Array> getByName(String name) {
        return http.send(
                HttpRequest.get()
                        .url("/getByName")
                        .addParam("name", name),
                Array.class
        );
    }

    @Override
    public Observable<List<StudentFile>> getAll() {
        return null;
    }

    @Override
    public void update(StudentFile studentFile) {

    }

    @Override
    public void save(StudentFile studentFile) {

    }
}
