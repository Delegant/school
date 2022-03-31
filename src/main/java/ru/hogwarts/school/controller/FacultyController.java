package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @PostMapping()
    public Faculty crateFaculty(@RequestBody Faculty faculty){
        return facultyService.crateFaculty(faculty);
    }

    @GetMapping("{id}")
    public Faculty getFaculty(@PathVariable long id){
       return facultyService.findFaculty(id);
    }

    @PutMapping()
    public Faculty updateFaculty(@RequestBody Faculty faculty){
       return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public Faculty deleteFaculty(@PathVariable long id){
        return facultyService.deleteFaculty(id);
    }

    @GetMapping("{color}")
    public Collection<Faculty> filterColorFacultyCollection(@PathVariable String color){
        return facultyService.filterColorFacultyCollection(color);
    }

}
