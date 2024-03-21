package com.example;

import com.example.ru.hogwarts.school.controller.FacultyController;
import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void contextLoadsTest() throws Exception {
        assertThat(facultyController).isNotNull();
    }

    @Test
    void testGetFaculty() {
        String response = this.restTemplate.getForObject("/faculty", String.class);
        assertNotNull(response);
        assertTrue(response.contains("Faculty of this application is Good!"));
    }

    @Test
    void infoFacultyTest() throws Exception {
        String response = this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class);

        assertThat(response).isNotNull();

        assertThat(response).isEqualTo("Faculty of this application is Good!");
    }

    @Test
    void postStudentTest() throws Exception {
        Faculty faculty = new Faculty(1, "Berychc", "Purple");
        faculty.setName("Bernadot");
        faculty.setColor("Green");

        ResponseEntity<Faculty> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void getFacultyStudentsTest() throws Exception {
        ResponseEntity<List<Student>> responseEntity =
                this.restTemplate.exchange("http://localhost:" + port + "/faculty/1/students",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {});

        List<Student> students = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(students).extracting(Student::getName).containsExactlyInAnyOrder("Berychc", "Bernadot");
    }

    @Test
    void readFacultyTest() throws Exception {
        ResponseEntity<Faculty> responseEntity = this.restTemplate.getForEntity
                ("http://localhost:" + port + "/faculty/1", Faculty.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Faculty faculty = responseEntity.getBody();

        assertThat(faculty).isNotNull();

        assertThat(faculty).isEqualTo(new Faculty(1, "Berychc", "Purple"));
    }

    @Test
    void editFacultyTest() throws Exception {
        Faculty expected = new Faculty(1, "Berychc", "Purple");
        expected.setName("Bernadot");
        expected.setColor("Green");

        ResponseEntity<Faculty> responseEntity = this.restTemplate.exchange("http://localhost:" + port + "/faculty/edit",
                HttpMethod.POST, new ResponseEntity<>(expected, HttpStatus.OK), Faculty.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Faculty faculty = responseEntity.getBody();

        assertThat(faculty).isNotNull();

        assertThat(faculty).isEqualTo(expected);
    }

    @Test
    void deleteFacultyTest() throws Exception {
        int facultyId = 1;

        this.restTemplate.delete("http://localhost:" + port + "/faculty/delete/" + facultyId);

        ResponseEntity<Faculty> responseEntity = this.restTemplate.getForEntity
                ("http://localhost:" + port + "/faculty/" + facultyId, Faculty.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void searchFacultiesTest() throws Exception {
        ResponseEntity<List<Faculty>> responseEntity = this.restTemplate.exchange
                ("http://localhost:" + port + "/faculty/search/Berychc/Purple",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Faculty>>() {});

        List<Faculty> faculty = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(faculty).extracting(Faculty::getName).containsExactlyInAnyOrder("Berychc", "Bernadot");
    }
}
