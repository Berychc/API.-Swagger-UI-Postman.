package com.example;

import com.example.ru.hogwarts.school.controller.FacultyController;
import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.repository.FacultyRepository;
import com.example.ru.hogwarts.school.service.FacultyService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    void getInfoFacultyTest() throws Exception {
        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(content().string("Faculty of this application is Good!"));
    }

    @Test
    void createFacultyTest() throws Exception {
        mockMvc.perform(post("/faculty/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Berychc\",\"color\":\"Purple\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void readFacultyTest() throws Exception {

        Faculty faculty = new Faculty(1L, "Berychc", "Purple");

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculty/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Berychc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("Purple"));
    }

    @Test
    void editFacultyTest() throws Exception {
        mockMvc.perform(put("/faculty/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Bernadot\",\"color\":\"Green\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFacultyTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Berychc", "Purple");

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(delete("/faculty/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void searchFacultiesTest() throws Exception {
        mockMvc.perform(get("/faculty/search/Berychc/Purple")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"Berychc\",\"color\":\"Purple\"}]"));
    }

    @Test
    void getFacultyStudentsTest() throws Exception {
        mockMvc.perform(get("/faculty/1/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
