package ru.hogwarts.school.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

//    @Query(value =  "select * from student where student.faculty_id = ?1",
//            nativeQuery = true)
    @Query("select s from Student s where s.faculty.id = :id")
    Collection<Student> getStudentsByFacultyId(long id);

}
