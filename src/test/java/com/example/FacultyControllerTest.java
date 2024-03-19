package com.example;

import com.example.ru.hogwarts.school.controller.FacultyController;
import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

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
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void infoFacultyTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isEqualTo("Faculty of this application is Good!");
    }

    @Test
    void getStudentTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotNull();
    }

    @Test
    void postStudentTest() throws Exception {
        Faculty faculty = new Faculty(1, "Berychc", "Purple");
        faculty.setName("Bernadot");
        faculty.setColor("Green");

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotNull();
    }

    @Test
    void getFacultyStudentsTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/faculty/1/students", String.class));
    }

    @Test
    void readFacultyTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/faculty/1", String.class));
    }

    @Test
    void editFacultyTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/faculty/edit", String.class));
    }

    @Test
    void deleteFacultyTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/faculty/delete/1", String.class));
    }

    @Test
    void searchFacultiesTest() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/faculty/search/Berychc/Purple", String.class));
    }
}
