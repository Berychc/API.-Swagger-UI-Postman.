package com.example;

import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.repository.StudentRepository;
import com.example.ru.hogwarts.school.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.example.ConstantStudentTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    StudentRepository repository;

    @InjectMocks
    StudentService service;

    @Test
    void createStudentTest() {
        Student expected = new Student(ID1, NAME1, AGE1);
        when(repository.save(expected)).thenReturn(expected);

        Student actual = service.createStudent(expected);

        verify(repository, only()).save(expected);
        assertEquals(expected, actual);;
    }

    @Test
    void readStudentTest() {
        Student expected = new Student(ID1, NAME1, AGE1);;
        when(repository.findById(ID1)).thenReturn(Optional.of(expected));

        Student actual = service.readStudent(ID1);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void editStudentTest() {
        Student expected = new Student(ID1, NAME1, AGE1);
        when(repository.save(expected)).thenReturn(expected);

        Student actualName = service.editStudent(expected);
        Student actualColor = service.editStudent(expected);
        actualName.setName(NAME2);
        actualColor.setAge(AGE2);

        assertEquals(expected, actualName);
        assertEquals(expected, actualColor);
    }

    @Test
    void removeStudentTest() {
        Student expected = new Student(ID1, NAME1, AGE1);
        when(repository.findById(ID1)).thenReturn(Optional.of(expected));

        service.removeStudent(ID1);

        assertTrue(repository.findById(ID1).isPresent());
    }

    @Test
    void getStudentsTest() {
        List<Student> studentsList = Arrays.asList(
                new Student(ID1, NAME1, AGE1),
                new Student(ID2, NAME2, AGE2));

        when(repository.findAll()).thenReturn(studentsList);

        Collection<Student> actual = service.getStudents();

        assertEquals(2,actual.size());
    }

}
