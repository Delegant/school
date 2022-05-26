package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;
    private final StudentService studentService;

    public FacultyController(FacultyService facultyService, StudentService studentService) {
        this.facultyService = facultyService;
        this.studentService = studentService;
    }

    @PostMapping()
    public ResponseEntity<Faculty> crateFaculty(@RequestBody Faculty faculty) {
        if (faculty.getId() != 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be empty");
        }
        Faculty cratedFaculty = facultyService.crateFaculty(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(cratedFaculty);
    }

    @GetMapping("{id}")
    public Faculty findFaculty(@PathVariable long id) {
        Faculty foundFaculty = facultyService.findFaculty(id);
        if (foundFaculty == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty doesn't exist.");
        }
        return foundFaculty;
    }

    @PutMapping()
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.findFaculty(faculty.getId());
        if (foundFaculty == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty doesn't exist.");
        }
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public Faculty deleteFaculty(@PathVariable long id) {
        Faculty foundFaculty = facultyService.findFaculty(id);
        if (foundFaculty == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty doesn't exist.");
        }
        facultyService.deleteFaculty(id);
        return foundFaculty;
    }

    @GetMapping("filter/{name}")
    public Collection<Faculty> filterNameFacultyCollection(@PathVariable String name) {
        return facultyService.filterNameFacultyCollection(name);
    }

    @GetMapping("students/{id}")
    public Collection<Student> getStudentsByFacultyId(@PathVariable long id) {
        return studentService.getStudentsByFacultyId(id);
    }

    @GetMapping("largest_name")
    public String getLargestName() {
        return facultyService.getLargestName();
    }
}
