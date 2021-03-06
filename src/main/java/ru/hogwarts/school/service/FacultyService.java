package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty crateFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty updateFaculty(Faculty faculty);

    void deleteFaculty(long id);

    Collection<Faculty> filterNameFacultyCollection(String name);

    Collection<Faculty> getFacultyByStudentName(String name);

    String getLargestName();
}
