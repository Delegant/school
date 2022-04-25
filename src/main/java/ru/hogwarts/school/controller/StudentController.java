package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Faculty;
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
    public ResponseEntity<Student> crateStudent(@RequestBody Student student) {
        if (student.getId() != 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be zero.");
        }
        Student createdStudent = studentService.crateStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @GetMapping("{id}")
    public Student findStudent(@PathVariable long id) {
        Student foundStudent = studentService.findStudent(id);
        if (foundStudent == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student must be created.");
        }
            return studentService.findStudent(id);
    }

    @PutMapping()
    public Student updateStudent(@RequestBody Student student) {
        Student foundStudent = studentService.findStudent(student.getId());
        if (foundStudent == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student must be created.");
        }
        return studentService.updateStudent(student);
    }

    @DeleteMapping("{id}")
    public Student deleteStudent(@PathVariable long id) {
        Student foundStudent = studentService.findStudent(id);
        if (foundStudent == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student must be created.");
        }
        studentService.deleteStudent(id);
        return foundStudent;
    }

    @GetMapping("filter/{age}")
    public Collection<Student> filterAgeStudentCollection(@PathVariable int age) {
        return studentService.filterAgeStudentCollection(age);
    }

    @GetMapping("filter/")
    public Collection<Student> filterAgeBetween(@RequestParam(name = "min") int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("faculty/{name}")
    public Faculty getFacultyByStudentId(@PathVariable String name) {
        return studentService.getFacultyByStudentId(name);
    }
}
