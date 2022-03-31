package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student crateStudent(Student student);

    Student findStudent(long id);

    Student updateStudent(Student student);

    Student deleteStudent(long id);

    Collection<Student> filterAgeStudentCollection(int age);
}
