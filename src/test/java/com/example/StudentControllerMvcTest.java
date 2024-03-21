package com.example;

import com.example.ru.hogwarts.school.controller.StudentController;
import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.repository.StudentRepository;
import com.example.ru.hogwarts.school.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;


    @Test
    void getInfoStudentTest() throws Exception {
        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student of this application is Good person!"));
    }

    @Test
    void readStudentTest() throws Exception {

        Student student = new Student(1L, "Berychc", 22);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Berychc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22));

    }

    @Test
    void getStudentByAgeTest() throws Exception {
        Student student = new Student(1L, "Berychc", 22);
        when(studentRepository.findAllByAge(22)).thenReturn(List.of(student));

        mockMvc.perform(get("/student/age?age=22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"Berychc\",\"age\":22}]"));
    }

    @Test
    void getStudentsByAgeBetweenTest() throws Exception {
        Student student = new Student(1L, "Berychc", 22);
        Student student2 = new Student(2L, "Bernadot", 23);

        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(Arrays.asList(student, student2));

        mockMvc.perform(get("/student/findByAgeBetween")
                        .param("min", "22")
                        .param("max", "23")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json
                        ("[{\"name\":\"Berychc\",\"age\":22},{\"name\":\"Bernadot\",\"age\":23}]"));
    }

    @Test
    void getStudentFacultyTest() throws Exception {
        Student student = new Student(1L, "Berychc", 22);
        Faculty faculty = new Faculty(1L, "Berychc", "Purple");
        student.setFaculty(faculty);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/student/1/faculty")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Berychc\",\"color\":\"Purple\"}"));
    }
}
