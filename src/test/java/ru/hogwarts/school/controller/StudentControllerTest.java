package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import ru.hogwarts.school.model.Student;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.hogwarts.school.constants.SchoolApplicationTestsConstants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    int port;
    Student studentResponse;
    long studentId;

    @Autowired
    StudentController studentController;

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeAll
    void addNewTestStudentAndAvatarPic() throws IOException {
        String url = LOCALHOST + port + STUDENT_ENDPOINT;
        Student student = new Student(ZERO_ID, STUDENT_TEST_NAME, STUDENT_TEST_AGE);
        studentResponse = restTemplate.postForObject(url, student, Student.class);
        studentId = studentResponse.getId();
        shouldReturnStatus200WhenPostTestAvatarPic();
    }

    @AfterAll
    void deleteTestStudentAndAvatar() {
        String url = LOCALHOST + port + STUDENT_ENDPOINT + "/" + studentId;
        restTemplate.delete(url, Student.class);
    }

    @Test
    void shouldReturnNotNullRequestAfterStartApp() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void shouldReturnNotZeroStudentIdAfterPost() {
        String url = LOCALHOST + port + STUDENT_ENDPOINT;
        Student student = new Student(ZERO_ID, STUDENT_TEST_NAME, STUDENT_TEST_AGE);
        Student responseStudent = restTemplate.postForObject(url, student, Student.class);
        Assertions.assertThat(studentId).isNotZero();
        restTemplate.delete(url + "/" + responseStudent.getId(), Student.class);
    }

    @Test
    void shouldReturnStatus200WhenPostTestAvatarPic() throws IOException {
        String urlPutAvatar = LOCALHOST + port + STUDENT_ENDPOINT + "/" + studentId + AVATAR_ENDPOINT;
        Path testPicPath = Files.createTempFile("testAvatarFile", ".png");
        BufferedImage image = new BufferedImage(1024, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.drawLine(0, 300, 1024, 0);
        graphics.drawLine(0, 0, 1024, 300);
        graphics.dispose();
        ImageIO.write(image, "png", testPicPath.toFile());

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
        body.add("avatar", new FileSystemResource(testPicPath));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<LinkedMultiValueMap<String, Object>>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(urlPutAvatar, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnZeroStudentIdAfterDeleteStudent() {
        String urlPost = LOCALHOST + port + STUDENT_ENDPOINT;
        Student student = new Student(ZERO_ID, STUDENT_TEST_NAME, STUDENT_TEST_AGE);
        Student studentResponse = restTemplate.postForObject(urlPost, student, Student.class);
        String urlGetAndDelete = LOCALHOST + port + STUDENT_ENDPOINT + "/" + studentResponse.getId();
        restTemplate.delete(urlGetAndDelete, Student.class);
        Assertions
                .assertThat(restTemplate.getForObject(urlGetAndDelete, Student.class))
                .hasFieldOrPropertyWithValue(ENTITY_FIELD_ID, 0L);
    }

    @Test
    void shouldReturnTestStudentNameAfterFind() throws Exception {
        String url = LOCALHOST + port + STUDENT_ENDPOINT + "/" + studentId;
        Assertions
                .assertThat(restTemplate.getForObject(url, Student.class))
                .hasFieldOrPropertyWithValue(ENTITY_FIELD_NAME, STUDENT_TEST_NAME);
    }

    @Test
    void shouldReturnUpdateStudentNameAfterPut() {
        String urlPut = LOCALHOST + port + STUDENT_ENDPOINT;
        String urlGet = urlPut + "/" + studentId;
        Student studentForTest2 = new Student(studentId, STUDENT_TEST_NAME2, STUDENT_TEST_AGE);
        restTemplate.put(urlPut, studentForTest2);

        Assertions
                .assertThat(restTemplate.getForObject(urlGet, Student.class))
                .hasFieldOrPropertyWithValue(ENTITY_FIELD_NAME, STUDENT_TEST_NAME2);
    }

    @Test
    void shouldReturnTestStudentNameWithAge32AfterFind() {
        String url = LOCALHOST + port + STUDENT_ENDPOINT + FILTER_ENDPOINT + STUDENT_TEST_AGE_ENDPOINT;
        Collection<LinkedHashMap<String, String>> response = restTemplate.getForObject(url, Collection.class);
        Assertions
                .assertThat(response)
                .extracting(x -> x.get(ENTITY_FIELD_NAME))
                .contains(STUDENT_TEST_NAME);
    }

    @Test
    void shouldReturnTestStudentNameWithAgeBetween30And32AfterFind() {
        String url = LOCALHOST + port + STUDENT_ENDPOINT + FILTER_ENDPOINT + "/?min=" + 30 + "&max=" + STUDENT_TEST_AGE;
        Collection<LinkedHashMap<String, String>> response = restTemplate.getForObject(url, Collection.class);
        Assertions
                .assertThat(response)
                .extracting(x -> x.get(ENTITY_FIELD_NAME))
                .contains(STUDENT_TEST_NAME);
    }

    @Test
    void shouldReturnFacultyByStudentNameAfterFind() {
        String urlPutFaculty = LOCALHOST + port + STUDENT_ENDPOINT + FACULTY_ENDPOINT + "/?student_id=" + studentId + "&faculty_id=" + ONE_ID;
        String urlGetStudent = LOCALHOST + port + STUDENT_ENDPOINT + FACULTY_ENDPOINT + "/" + studentResponse.getName();
        restTemplate.put(urlPutFaculty, null);
        Collection<LinkedHashMap<String, Integer>> response = restTemplate.getForObject(urlGetStudent, Collection.class);
        assertThat(response)
                .extracting(x -> x.get(ENTITY_FIELD_ID))
                .contains(ONE_ID);
    }

    @Test
    void shouldReturnNotNullAvatarPicWhenGetIt() {
        String urlPutAvatar = LOCALHOST + port + STUDENT_ENDPOINT + "/" + studentId + AVATAR_ENDPOINT;
        assertThat(restTemplate.getForObject(urlPutAvatar, String.class)).isNotNull();
    }

    @Test
    void shouldReturnNotNullAvatarPreviewPicWhenGetIt() {
        String urlPutAvatar = LOCALHOST + port + STUDENT_ENDPOINT + "/" + studentId + AVATAR_PREVIEW_ENDPOINT;
        assertThat(restTemplate.getForObject(urlPutAvatar, String.class)).isNotNull();
    }
}
