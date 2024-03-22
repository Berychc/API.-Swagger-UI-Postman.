package com.example;

import com.example.ru.hogwarts.school.controller.StudentController;
import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.model.Student;
import org.assertj.core.api.Assertions;
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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;



    @Test
    void contextLoadsTest() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudent() {
        String response = this.restTemplate.getForObject("/student", String.class);
        assertNotNull(response);
        assertTrue(response.contains("Student of this application is Good person!"));
    }
    @Test
    void infoStudentTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isEqualTo("Student of this application is Good person!");
    }

    @Test
    void createStudentTest() {
        Student student = new Student(1, "Berychc", 22);

        ResponseEntity<Student> response = restTemplate.postForEntity("/student/create", student, Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());

    }

    @Test
    void editStudentTest() throws Exception {
        Student student = new Student(1, "Berychc", 22);
        student.setName("Bernadot");
        student.setAge(23);

    }

    @Test
    void readStudentTest() throws Exception {
        long studentId = 1;
        Student student = new Student(studentId, "Berychc", 22);

        ResponseEntity<Student> response = studentController.readStudent(studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());

    }

    @Test
    void deleteStudentTest() throws Exception {
        int studentId = 1;

        this.restTemplate.delete("http://localhost:" + port + "/student/delete/" + studentId);

        ResponseEntity<Student> responseEntity = this.restTemplate.getForEntity
                ("http://localhost:" + port + "/student/" + studentId, Student.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
