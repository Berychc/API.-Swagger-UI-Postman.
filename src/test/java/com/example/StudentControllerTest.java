package com.example;

import com.example.ru.hogwarts.school.controller.StudentController;
import com.example.ru.hogwarts.school.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
    void postStudentTest() throws Exception {

    Student student = new Student(1, "Berychc", 22);
        student.setName("Bernadot");
        student.setAge(23);

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    void getStudentByAgeTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/student/age", String.class));
    }

    @Test
    void readStudentTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/student/1", String.class));
    }

    @Test
    void editStudentTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/student/edit", String.class));
    }

    @Test
    void deleteStudentTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/student/delete/1", String.class));
    }

    @Test
    void getStudentsByAgeBetweenTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/student//findByAgeBetween/min/max", String.class));
    }

    @Test
    void getStudentFacultyTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/student/1/faculty", String.class));
    }
}
