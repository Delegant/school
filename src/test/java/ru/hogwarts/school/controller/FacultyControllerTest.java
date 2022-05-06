package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import ru.hogwarts.school.model.Faculty;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.impl.AvatarServiceImpl;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.constants.SchoolApplicationTestsConstants.*;
import static org.mockito.Mockito.*;

@WebMvcTest
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private AvatarServiceImpl avatarService;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @SpyBean
    private StudentServiceImpl studentService;

    @SpyBean
    private StudentController studentController;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void shouldReturnCorrectFacultyWhenPostIt() throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put(ENTITY_FIELD_NAME, TEST_FACULTY_NAME);
        facultyObject.put(ENTITY_FIELD_COLOR, TEST_FACULTY_COLOR);

        Faculty faculty = new Faculty();
        faculty.setId(ONE_ID_LONG);
        faculty.setName(TEST_FACULTY_NAME);
        faculty.setColor(TEST_FACULTY_COLOR);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ONE_ID))
                .andExpect(jsonPath("$.name").value(TEST_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(TEST_FACULTY_COLOR));
    }

    @Test
    public void shouldReturnCorrectFacultyWhenGetIt() throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put(ENTITY_FIELD_NAME, TEST_FACULTY_NAME);
        facultyObject.put(ENTITY_FIELD_COLOR, TEST_FACULTY_COLOR);

        Faculty faculty = new Faculty();
        faculty.setId(ONE_ID_LONG);
        faculty.setName(TEST_FACULTY_NAME);
        faculty.setColor(TEST_FACULTY_COLOR);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + ONE_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ONE_ID))
                .andExpect(jsonPath("$.name").value(TEST_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(TEST_FACULTY_COLOR));
    }

    @Test
    public void shouldReturnUpdatedFacultyWhenPutIt() throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put(ENTITY_FIELD_NAME, TEST_FACULTY_NAME);
        facultyObject.put(ENTITY_FIELD_COLOR, TEST_FACULTY_COLOR);

        JSONObject updatedFacultyObject = new JSONObject();
        facultyObject.put(ENTITY_FIELD_NAME, UPDATED_TEST_FACULTY_NAME);
        facultyObject.put(ENTITY_FIELD_COLOR, UPDATED_TEST_FACULTY_COLOR);

        Faculty faculty = new Faculty();
        faculty.setId(ONE_ID_LONG);
        faculty.setName(UPDATED_TEST_FACULTY_NAME);
        faculty.setColor(UPDATED_TEST_FACULTY_COLOR);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(updatedFacultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + ONE_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ONE_ID))
                .andExpect(jsonPath("$.name").value(UPDATED_TEST_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(UPDATED_TEST_FACULTY_COLOR));
    }

    @Test
    public void shouldNotReturnFacultyWhenDeleteIt() throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put(ENTITY_FIELD_NAME, TEST_FACULTY_NAME);
        facultyObject.put(ENTITY_FIELD_COLOR, TEST_FACULTY_COLOR);

        Faculty faculty = new Faculty();
        faculty.setId(ONE_ID_LONG);
        faculty.setName(UPDATED_TEST_FACULTY_NAME);
        faculty.setColor(UPDATED_TEST_FACULTY_COLOR);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + ONE_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ONE_ID))
                .andExpect(jsonPath("$.name").value(UPDATED_TEST_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(UPDATED_TEST_FACULTY_COLOR));
    }

    @Test
    public void shouldReturnStudentsWhenGetTheyByIdFaculty () throws Exception {
        Student student = new Student();
        student.setId(ONE_ID_LONG);
        student.setName(STUDENT_TEST_NAME);
        student.setAge(STUDENT_TEST_AGE);

        Collection<Student> studentsCollection = new ArrayList<>();
        studentsCollection.add(student);

        when(studentRepository.getStudentsByFacultyId((eq(ONE_ID_LONG)))).thenReturn(studentsCollection);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/students/" + ONE_ID_LONG)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(ONE_ID)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder(STUDENT_TEST_NAME)))
                .andExpect(jsonPath("$[*].age", containsInAnyOrder(STUDENT_TEST_AGE)));
    }

    @Test
    public void shouldReturnFacultyWhenGetByFilterName () throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put(ENTITY_FIELD_NAME, TEST_FACULTY_NAME);
        facultyObject.put(ENTITY_FIELD_COLOR, TEST_FACULTY_COLOR);

        Faculty faculty = new Faculty();
        faculty.setId(ONE_ID_LONG);
        faculty.setName(TEST_FACULTY_NAME);
        faculty.setColor(TEST_FACULTY_COLOR);

        Collection<Faculty> facultyCollection = new ArrayList<>();
        facultyCollection.add(faculty);

        when(facultyRepository.findByNameIgnoreCase((eq(TEST_FACULTY_NAME)))).thenReturn(facultyCollection);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter/" + TEST_FACULTY_NAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(ONE_ID)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder(TEST_FACULTY_NAME)))
                .andExpect(jsonPath("$[*].color", containsInAnyOrder(TEST_FACULTY_COLOR)));
    }

}
