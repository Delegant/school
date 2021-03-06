package ru.hogwarts.school.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

//    @Query(value =  "select * from student where student.faculty_id = ?1",
//            nativeQuery = true)
    @Query("select s from Student s where s.faculty.id = :id")
    Collection<Student> getStudentsByFacultyId(long id);

    Collection<Student> findByName(String name);

    @Query("select count(s) from Student as s")
    int getCountStudents();

    @Query("select  avg(s.age) from Student s")
    float getAverageAgeStudents();

    @Query(value = "select * from student order by id desc limit 5",
            nativeQuery = true)
    Collection<Student> get5LastStudents();

}
