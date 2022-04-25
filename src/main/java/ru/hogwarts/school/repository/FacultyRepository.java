package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByNameIgnoreCase(String name);

    @Query("select f from Faculty f, Student s where s.name like :name and f.id = s.faculty.id")
    Faculty getFacultyByStudentId(String name);

}
