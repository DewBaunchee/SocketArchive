package by.varyvoda.matvey.server.app.dao.iface;

import by.varyvoda.matvey.common.app.entity.StudentFile;

import java.util.List;

public interface IStudentFileDao extends IDao<StudentFile> {

    List<StudentFile> getByName(String name);

    List<StudentFile> getAll();

    StudentFile getById(Integer id);
}
