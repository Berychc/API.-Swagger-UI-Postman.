package com.example;

import com.example.ru.hogwarts.school.controller.FacultyController;
import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.repository.FacultyRepository;
import com.example.ru.hogwarts.school.service.FacultyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    List<Faculty> savedFaculties;

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        Faculty faculty1 = new Faculty(1, "Berychc", "Purple");
        Faculty faculty2 = new Faculty(2, "Bernadot", "Green");
        List<Faculty> facultiesList = List.of(faculty1, faculty2);

        savedFaculties = facultyRepository.saveAll(facultiesList);
    }


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
    void createFacultyTest() throws JsonProcessingException, JSONException {

        Faculty faculty = new Faculty(24, "Ghost", "Black");

        String expected = mapper.writeValueAsString(faculty);

        ResponseEntity<String> response = restTemplate.postForEntity("/faculty/create", faculty, String.class);

        assertEquals(HttpStatus.OK , response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

    }
    @Test
    void readFacultyTest() throws Exception {
        long facultyId = 1;
        Faculty faculty = new Faculty(facultyId, "Berychc", "Purple");

        Mockito.when(facultyService.getFacultyById(facultyId)).thenReturn(faculty);

        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculties/{id}", Faculty.class, facultyId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(faculty, response.getBody());
    }

    @Test
    void editFacultyTest() throws Exception {
        Faculty faculty = new Faculty(1, "Berychc", "Purple");

        HttpEntity<Faculty> entity = new HttpEntity<>(faculty);
        faculty.setId(savedFaculties.get(0).getId());

        ResponseEntity<Faculty> response = restTemplate.exchange
                ("/faculty/edit", HttpMethod.PUT, entity, Faculty.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(faculty, response.getBody());
    }

    @Test
    void deleteFacultyTest() throws Exception {
        int facultyId = 1;

        this.restTemplate.delete("http://localhost:" + port + "/faculty/delete/" + facultyId);

        ResponseEntity<Faculty> responseEntity = this.restTemplate.getForEntity
                ("http://localhost:" + port + "/faculty/" + facultyId, Faculty.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
