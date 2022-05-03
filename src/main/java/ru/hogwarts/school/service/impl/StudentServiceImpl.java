package ru.hogwarts.school.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Student crateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public boolean findStudent(String name) {
        return studentRepository.findByName(name).size() > 0;
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> filterAgeStudentCollection(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public  Collection<Student> findByAgeBetween (int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Collection<Student> getStudentsByFacultyId(long id) {
        return studentRepository.getStudentsByFacultyId(id);
    }

}
