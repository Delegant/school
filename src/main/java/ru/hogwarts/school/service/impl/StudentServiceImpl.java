package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Map<Long, Student> studentMap = new HashMap<>();
    private long id = 0;

    @Override
    public Student crateStudent(Student student) {
        student.setId(++id);
        studentMap.put(id, student);
        return student;
    }

    @Override
    public Student findStudent(long id) {
        return studentMap.get(id);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentMap.put(student.getId(), student);
    }

    @Override
    public Student deleteStudent(long id) {
        return studentMap.remove(id);
    }

    @Override
    public Collection<Student> filterAgeStudentCollection(int age) {
        return studentMap.values().stream()
                .filter((s) -> s.getAge() == age)
                .collect(Collectors.toList());
    }

}
