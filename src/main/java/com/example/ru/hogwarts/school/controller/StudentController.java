package com.example.ru.hogwarts.school.controller;

import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final List<Student> students = new ArrayList<>();
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = service.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = service.readStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editStudent = service.editStudent(student);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        Student removeStudent = service.removeStudent(id);
        if (removeStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(removeStudent);
    }

    @GetMapping("/age/{age}")
    public List<Student> getStudentByAge(@PathVariable Integer age) {
        return students.stream().filter(student -> student.getAge() == age).collect(Collectors.toList());
    }
}
