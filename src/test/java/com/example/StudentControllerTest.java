package com.example;

import com.example.ru.hogwarts.school.controller.StudentController;
import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.repository.StudentRepository;
import com.example.ru.hogwarts.school.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    List<Student> savedStudents;

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentService studentService;

    private static final ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        Student student1 = new Student(1L, "Berychc", 22);
        Student student2 = new Student(2L, "Bernadot", 23);
        List<Student> studentsList = List.of(student1, student2);

        savedStudents = studentRepository.saveAll(studentsList);
    }

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
    void createStudentTest() throws JsonProcessingException, JSONException {

        Student student = new Student(3, "Soup", 26);
        String expected = mapper.writeValueAsString(student);

        ResponseEntity<String> response = restTemplate.postForEntity("/student/create", student, String.class);

        assertEquals(HttpStatus.OK , response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    void editStudentTest() throws Exception {
        Student student = new Student(1, "Berychc", 22);

        HttpEntity<Student> entity = new HttpEntity<>(student);
        student.setId(savedStudents.get(0).getId());

        ResponseEntity<Student> response = restTemplate.exchange("/student/edit", HttpMethod.PUT, entity, Student.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(student, response.getBody());
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
