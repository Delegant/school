package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;

@RequestMapping("student")
@RestController
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;
    private final FacultyService facultyService;


    public StudentController(StudentService studentService, AvatarService avatarService, FacultyService facultyService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
        this.facultyService = facultyService;
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
        studentService.findStudent(id);
        return studentService.findStudent(id);
    }

    @PutMapping("faculty/")
    public Student setStudentFaculty(@RequestParam(name = "student_id") long student_id,
                                     @RequestParam(name = "faculty_id") long faculty_id) {
        Faculty foundFaculty = facultyService.findFaculty(faculty_id);
        Student foundStudent = studentService.findStudent(student_id);
        foundStudent.setFaculty(foundFaculty);
        return studentService.updateStudent(foundStudent);
    }

    @PutMapping()
    public Student updateStudent(@RequestBody Student student) {
        studentService.findStudent(student.getId());
        return studentService.updateStudent(student);
    }

    @DeleteMapping("{id}")
    public Student deleteStudent(@PathVariable long id) throws IOException {
        Student foundStudent = studentService.findStudent(id);
        if (avatarService.findAvatar(id).getFilePath() != null) {
            avatarService.deleteAvatarFile(id);
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
    public Collection<Faculty> getFacultyByStudentName(@PathVariable String name) {
        if (!studentService.findStudent(name)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student must be created.");
        }
        return facultyService.getFacultyByStudentName(name);
    }

    @PostMapping(value = "{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable long id, @RequestParam("avatar") MultipartFile avatar) throws IOException {
        studentService.findStudent(id);
        if (avatar.getSize() > 1024 * 300 || avatar.getSize() == 0L) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pic not correct");
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
