package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student crateStudent(Student student);

    Student findStudent(long id);

    Student updateStudent(Student student);

    void deleteStudent(long id);

    Collection<Student> filterAgeStudentCollection(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Faculty getFacultyByStudentId(String name);
}
