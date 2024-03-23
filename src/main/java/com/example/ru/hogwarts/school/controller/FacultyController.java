package com.example.ru.hogwarts.school.controller;

import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getInfoFaculty() {
        return ResponseEntity.ok("Faculty of this application is Good!");
    }

    @PostMapping("/create")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = service.createFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> readFaculty(@PathVariable Long id) {
        Faculty faculty = service.readFaculty(id);
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("/edit")
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editFaculty = service.editFaculty(faculty);
        return new ResponseEntity<>(editFaculty , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity removeFaculty(@PathVariable Long id) {
        service.removeFaculty(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/search")
    public List<Faculty> searchFaculties(@RequestParam("facultyName") String facultyName,
                                         @RequestParam("color") String color) {
        return service.searchFaculties(facultyName, color);
    }

    @GetMapping("/{facultyId}/students")
    public ResponseEntity<List<Student>> getFacultyStudents(@PathVariable("facultyId") Long facultyId) {
        List<Student> students = service.getFacultyStudents(facultyId);
        return ResponseEntity.ok().body(students);
    }
}
