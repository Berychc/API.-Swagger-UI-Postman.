package com.example;

import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.service.FacultyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;

import static com.example.ConstantFacultyTest.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    FacultyService service;

    @Test
    void createFaculty() {
        Faculty expected = new Faculty(FID1, FNAME1, FCOLOR1);
        service.createFaculty(expected);

        Faculty actual = FACULTY1;

        assertEquals(expected, actual);

    }

    @Test
    void readFacultyTest() {
        Faculty expected = new Faculty(FID1, FNAME1, FCOLOR1);
        service.createFaculty(expected);


        assertEquals(expected, service.readFaculty(FID1));
    }

    @Test
    void editFacultyTest() {
        Faculty expected = new Faculty(FID1, FNAME1, FCOLOR1);
        service.createFaculty(expected);

        expected.setName(FNAME2);
        expected.setColor("Green");

        assertEquals(FNAME2, expected.getName());
        assertEquals(FCOLOR2, expected.getColor());
    }

    @Test
    void removeFacultyTest() {
        Faculty expected = new Faculty(FID1, FNAME1, FCOLOR1);
        service.createFaculty(expected);
        service.removeFaculty(FID1);
    }

    @Test
    void getFacultyTest() {
        Faculty faculty1 = new Faculty(FID1, FNAME1, FCOLOR1);
        Faculty faculty2 = new Faculty(FID1, FNAME1, FCOLOR1);
        service.createFaculty(faculty1);
        service.createFaculty(faculty2);

        Collection<Faculty> faculties = service.getFaculties();

        assertTrue(faculties.contains(faculty1));
        assertTrue(faculties.contains(faculty2));
    }
}
