package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Student {
    public Student() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    private String name;
    private int age;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @OneToOne (mappedBy = "student", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Avatar avatar;

    public Student(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id == student.id && age == student.age && Objects.equals(name, student.name) && Objects.equals(faculty, student.faculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, faculty);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", faculty=" + faculty +
                '}';
    }

}
