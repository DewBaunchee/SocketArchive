package by.varyvoda.matvey.server.app.service;

import by.varyvoda.matvey.common.app.entity.StudentFile;
import by.varyvoda.matvey.server.app.ServiceLocator;
import by.varyvoda.matvey.server.app.dao.iface.IStudentFileDao;
import by.varyvoda.matvey.server.app.dao.student_file.IStudentFileService;

import java.util.List;

public class StudentFileService implements IStudentFileService {

    private final IStudentFileDao studentFileDao = ServiceLocator.locate(IStudentFileDao.class);

    @Override
    public List<StudentFile> getByName(String name) {
        return studentFileDao.getByName(name);
    }

    @Override
    public List<StudentFile> getAll() {
        return studentFileDao.getAll();
    }

    @Override
    public StudentFile getById(Integer id) {
        return studentFileDao.getById(id);
    }

    @Override
    public void update(StudentFile studentFile) {
        studentFileDao.update(studentFile);
    }

    @Override
    public void save(StudentFile studentFile) {
        studentFileDao.save(studentFile);
    }
}
