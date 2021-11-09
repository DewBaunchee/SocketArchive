package by.varyvoda.matvey.client.service;

import by.varyvoda.matvey.client.config.Config;
import by.varyvoda.matvey.client.http.HttpClient;
import by.varyvoda.matvey.common.entity.StudentFile;

import java.util.List;

public class FileService implements IFileService {

    private final HttpClient http = new HttpClient(Config.serverUrl, Config.port);

    @Override
    public List<StudentFile> getByName(String name) {
        return null;
    }

    @Override
    public List<StudentFile> getAll() {
        return null;
    }

    @Override
    public void update(StudentFile studentFile) {

    }

    @Override
    public void save(StudentFile studentFile) {

    }
}
