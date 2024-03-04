package com.example;

import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Collection;

import static com.example.ConstantStudentTest.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    StudentService service;

    @Test
    void createStudentTest() {
        Student expected = new Student(ID1, NAME1, AGE1);
        service.createStudent(expected);

        Student actual = STUDENT1;

        assertEquals(expected, actual);


    }

    @Test
    void readStudentTest() {
        Student expected = new Student(ID1, NAME1, AGE1);
        service.createStudent(expected);

        assertEquals(expected, service.readStudent(ID1));
    }

    @Test
    void editStudentTest() {
        Student expected = new Student(ID1, NAME1, AGE1);
        service.createStudent(expected);

        expected.setName(NAME2);
        expected.setAge(22);

        assertEquals(NAME2, expected.getName());
        assertEquals(AGE2, expected.getAge());
    }

    @Test
    void removeStudentTest() {
        Student expected = new Student(ID1, NAME1, AGE1);
        service.createStudent(expected);
        service.removeStudent(ID1);
    }

    @Test
    void getStudentsTest() {
        Student student1 = new Student(ID1, NAME1, AGE1);
        Student student2 = new Student(ID2, NAME2, AGE2);
        service.createStudent(student1);
        service.createStudent(student2);

        Collection<Student> students = service.getStudents();

        assertTrue(students.contains(student1));
        assertTrue(students.contains(student2));
    }
}
