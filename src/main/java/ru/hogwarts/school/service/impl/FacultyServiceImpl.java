package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private long id = 0;


    @Override
    public Faculty crateFaculty(Faculty faculty) {
        faculty.setId(++id);
        return facultyMap.put(faculty.getId(),faculty);
    }

    @Override
    public Faculty findFaculty(long id) {
        return facultyMap.get(id);
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        return facultyMap.put(faculty.getId(),faculty);    }

    @Override
    public Faculty deleteFaculty(long id) {
        return facultyMap.remove(id);
    }

    @Override
    public Collection<Faculty> filterColorFacultyCollection(String color) {
        return facultyMap.values().stream()
                .filter((s)->s.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
