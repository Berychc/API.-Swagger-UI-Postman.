package com.example;

import com.example.ru.hogwarts.school.controller.FacultyController;
import com.example.ru.hogwarts.school.model.Faculty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isEqualTo("Faculty of this application is Good!");
    }

    @Test
    void postStudentTest() throws Exception {
        Faculty faculty = new Faculty(1, "Berychc", "Purple");
        faculty.setName("Bernadot");
        faculty.setColor("Green");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotNull();
    }

    @Test
    void getFacultyStudentsTest() throws Exception {
        assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/faculty/1/students", String.class));
    }

    @Test
    void readFacultyTest() throws Exception {
        assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/faculty/1", String.class));
    }

    @Test
    void editFacultyTest() throws Exception {
        assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/faculty/edit", String.class));
    }

    @Test
    void deleteFacultyTest() throws Exception {
        assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/faculty/delete/1", String.class));
    }

    @Test
    void searchFacultiesTest() throws Exception {
        assertThat(this.restTemplate.getForObject
                ("http://localhost:" + port + "/faculty/search/Berychc/Purple", String.class));
    }
}
