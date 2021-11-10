package by.varyvoda.matvey.client.service;

import by.varyvoda.matvey.client.observable.Observable;
import by.varyvoda.matvey.common.entity.StudentFile;

import java.lang.reflect.Array;
import java.util.List;

public interface IFileService {

    Observable<Array> getByName(String name);

    Observable<List<StudentFile>> getAll();

    void update(StudentFile studentFile);

    void save(StudentFile studentFile);
}
