package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Student crateStudent(Student student) {
        logger.debug("Was invoked method for create student");
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        logger.debug("Was invoked method for find student");
        return studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student must be created."));
    }

    @Override
    public boolean findStudent(String name) {
        logger.debug("Was invoked method for find student by name");
        return studentRepository.findByName(name).size() > 0;
    }

    @Override
    public Student updateStudent(Student student) {
        logger.debug("Was invoked method for save student");
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        logger.debug("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> filterAgeStudentCollection(int age) {
        logger.debug("Was invoked method for find student by age");
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.debug("Was invoked method for find student between min and max age");
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Collection<Student> getStudentsByFacultyId(long id) {
        logger.debug("Was invoked method for find students by faculty ID");
        return studentRepository.getStudentsByFacultyId(id);
    }

    @Override
    public int getCountStudents() {
        logger.debug("Was invoked method for get count students");
        return studentRepository.getCountStudents();
    }

    @Override
    public float getAverageAgeStudents() {
        logger.debug("Was invoked method for get average age students");
        return studentRepository.getAverageAgeStudents();
    }

    @Override
    public Collection<Student> get5LastStudents() {
        logger.debug("Was invoked method for get five last students");
        return studentRepository.get5LastStudents();
    }

}
