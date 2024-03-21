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

        Student createdStudent = this.restTemplate.postForObject("/student", student, Student.class);

        assertNotNull(createdStudent);
        assertEquals("Bernadot", createdStudent.getName());
        assertEquals(23, createdStudent.getAge());
    }

    @Test
    void getStudentByAgeTest() throws Exception {
        int expectedAge = 22;

        ResponseEntity<Student> responseEntity = this.restTemplate.getForEntity
                ("http://localhost:" + port + "/student/age", Student.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Student student = responseEntity.getBody();

        assertEquals(expectedAge, student.getAge());
    }

    @Test
    void readStudentTest() throws Exception {
        int expectedId = 1;

        ResponseEntity<Student> responseEntity = this.restTemplate.getForEntity
                ("http://localhost:" + port + "/student/1", Student.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Student student = responseEntity.getBody();

        assertEquals(expectedId, student.getId());
    }

    @Test
    void editStudentTest() throws Exception {
        Student expected = new Student(1, "Berychc", 22);
        expected.setName("Bernadot");
        expected.setAge(23);

        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity
                ("http://localhost:" + port + "/student/edit", String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String responseBody = responseEntity.getBody();

        assertEquals(expected, responseBody);
    }

    @Test
    void deleteStudentTest() throws Exception {
        int studentId = 1;

        this.restTemplate.delete("http://localhost:" + port + "/student/delete/" + studentId);

        ResponseEntity<Student> responseEntity = this.restTemplate.getForEntity
                ("http://localhost:" + port + "/student/" + studentId, Student.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getStudentsByAgeBetweenTest() throws Exception {
        Student student1 = new Student(1,"Alice", 20);
        Student student2 = new Student(2,"Bob", 25);
        Student student3 = new Student(3, "Charlie", 30);


        int minAge = 20;
        int maxAge = 30;
        ResponseEntity<List<Student>> responseEntity = this.restTemplate.exchange(
                "http://localhost:" + port + "/student/findByAgeBetween?min=" + minAge + "&max=" + maxAge,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {});

        List<Student> students = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(students).extracting(Student::getName).containsExactlyInAnyOrder("Alice", "Bob");
    }
}
