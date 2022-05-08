package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student crateStudent(Student student);

    Student findStudent(long id);

    boolean findStudent(String name);

    Student updateStudent(Student student);

    void deleteStudent(long id);

    Collection<Student> filterAgeStudentCollection(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> getStudentsByFacultyId(long id);

    int getCountStudents();

    float getAverageAgeStudents();

    Collection<Student> get5LastStudents();

}
