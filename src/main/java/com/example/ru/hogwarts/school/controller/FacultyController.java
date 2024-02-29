package com.example.ru.hogwarts.school.controller;

import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final List<Faculty> faculties = new ArrayList<>();
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = service.createFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = service.readFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editFaculty = service.editFaculty(faculty);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id) {
        Faculty removeFaculty = service.removeFaculty(id);
        if (removeFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(removeFaculty);
    }

    @GetMapping("/color/{color}")
    public List<Faculty> getFacultiesByColor(@PathVariable String color) {
        return faculties.stream().filter(faculty -> faculty.getColor()
                .equalsIgnoreCase(color)).collect(Collectors.toList());
    }
}
