package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RequestMapping("student")
@RestController
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;


    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping()
    public ResponseEntity<Student> crateStudent(@RequestBody Student student) {
        if (student.getId() != 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be zero.");
        }
        Student createdStudent = studentService.crateStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @GetMapping("{id}")
    public Student findStudent(@PathVariable long id) {
        Student foundStudent = studentService.findStudent(id);
        if (foundStudent == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student must be created.");
        }
        return studentService.findStudent(id);
    }

    @PutMapping()
    public Student updateStudent(@RequestBody Student student) {
        Student foundStudent = studentService.findStudent(student.getId());
        if (foundStudent == null) {
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

    @PostMapping(value = "{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pic is too big");
        }
        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{id}/avatar")
    public void getAvatar(@PathVariable long id, HttpServletResponse response) throws IOException {
        avatarService.getAvatar(id, response);
    }

    @GetMapping(value = "{id}/avatar/preview")
    public ResponseEntity<byte[]> getAvatarPreview(@PathVariable long id) {
        Avatar avatar = avatarService.findAvatar(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        httpHeaders.setContentLength(avatar.getPreview().length);
        return ResponseEntity.ok().headers(httpHeaders).body(avatar.getPreview());
    }
}
