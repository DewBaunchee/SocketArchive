package by.varyvoda.matvey.client.service;

import by.varyvoda.matvey.common.entity.StudentFile;

import java.util.List;

public interface IFileService {

    List<StudentFile> getByName(String name);

    List<StudentFile> getAll();

    void update(StudentFile studentFile);

    void save(StudentFile studentFile);
}
