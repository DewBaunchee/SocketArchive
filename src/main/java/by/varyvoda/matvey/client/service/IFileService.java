package by.varyvoda.matvey.client.service;

import by.varyvoda.matvey.client.observable.Observable;
import by.varyvoda.matvey.common.app.entity.StudentFile;

import java.util.List;

public interface IFileService {

    Observable<List<StudentFile>> getByName(String name);

    Observable<List<StudentFile>> getAll();

    void update(StudentFile studentFile);

    void save(StudentFile studentFile);

    Observable<StudentFile> getById(Integer id);
}
