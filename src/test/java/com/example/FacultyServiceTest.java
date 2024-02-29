package com.example;

import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.service.FacultyService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static com.example.ConstantFacultyTest.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FacultyServiceTest {

    private final FacultyService service = new FacultyService();

    @Test
    void createFaculty() {
        Faculty expected = new Faculty(FID1, FNAME1, FCOLOR1);

        assertEquals(0,service.getFaculties().size());
        assertFalse(service.getFaculties().contains(expected));

        Faculty actual = service.createFaculty(expected);

        assertEquals(expected, actual);

        assertEquals(1,service.getFaculties().size());
        assertTrue(service.getFaculties().contains(expected));
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

        assertEquals(expected, service.removeFaculty(FID1));
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
