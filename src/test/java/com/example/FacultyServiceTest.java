package com.example;

import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.repository.FacultyRepository;
import com.example.ru.hogwarts.school.service.FacultyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.example.ConstantFacultyTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    FacultyRepository repository;

    @InjectMocks
    FacultyService service;

    @Test
    void createFaculty() {
        Faculty expected = new Faculty(FID1, FNAME1, FCOLOR1);
        when(repository.save(expected)).thenReturn(expected);

        Faculty actual = service.createFaculty(expected);

        verify(repository, only()).save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void readFacultyTest() {
        Faculty expected = new Faculty(FID1, FNAME1, FCOLOR1);
        when(repository.findById(FID1)).thenReturn(Optional.of(expected));

        Faculty actual = service.readFaculty(FID1);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void editFacultyTest() {
        Faculty expected = new Faculty(FID1, FNAME1, FCOLOR1);
        when(repository.save(expected)).thenReturn(expected);

        Faculty actualName = service.editFaculty(expected);
        Faculty actualColor = service.editFaculty(expected);
        actualName.setName(FNAME2);
        actualColor.setColor(FCOLOR2);

        assertEquals(expected, actualName);
        assertEquals(expected, actualColor);
    }

    @Test
    void removeFacultyTest() {
        Faculty expected = new Faculty(FID1, FNAME1, FCOLOR1);
        when(repository.findById(FID1)).thenReturn(Optional.of(expected));

        service.removeFaculty(FID1);

        assertTrue(repository.findById(FID1).isPresent());
    }

    @Test
    void getFacultyTest() {
        List<Faculty> facultiesList = Arrays.asList(
                new Faculty(FID1, FNAME1, FCOLOR1),
                new Faculty(FID1, FNAME1, FCOLOR1));

        when(repository.findAll()).thenReturn(facultiesList);

        Collection<Faculty> actual = service.getFaculties();

        assertEquals(2,actual.size());
    }
}
