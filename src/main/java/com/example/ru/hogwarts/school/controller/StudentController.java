package com.example.ru.hogwarts.school.controller;

import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getInfoStudent() {
        return ResponseEntity.ok("Student of this application is Good person!");
    }

    @PostMapping("/create")
    public ResponseEntity createStudent(@RequestBody(required = false) Student student) {
        Student createdStudent = service.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> readStudent(@PathVariable Long id) {
        Student student = service.readStudent(id);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> editStudent(@RequestBody Student student) {
        service.editStudent(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity removeStudent(@PathVariable Long id) {
        service.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age")
    public List<Student> getStudentByAge(@RequestParam("age") Integer age) {
        return service.getStudentByAge(age);
    }

    @GetMapping("/findByAgeBetween")
    public List<Student> getStudentsByAgeBetween(@RequestParam("min") Integer min,
                                                 @RequestParam("max") Integer max) {
        return service.getStudentsByAgeBetween(min, max);
    }

    @GetMapping("/{studentId}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable long studentId) {
        Faculty faculty = service.getStudentFaculty(studentId);
        return ResponseEntity.ok().body(faculty);
    }
}
