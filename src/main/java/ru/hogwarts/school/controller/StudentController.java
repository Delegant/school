package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RequestMapping("student")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    public Student crateStudent(@RequestBody Student student) {
        return studentService.crateStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> findStudent(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
          return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping()
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student foundStudent = studentService.updateStudent(student);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("filter/{age}")
    public Collection<Student> filterAgeStudentCollection(@PathVariable int age) {
        return studentService.filterAgeStudentCollection(age);
    }
}
