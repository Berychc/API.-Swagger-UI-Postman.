package com.example.ru.hogwarts.school.service;

import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public Student readStudent(long id) {
        return repository.findById(id).orElseThrow();
    }

    public Student editStudent(Student student) {
        return repository.save(student);
    }

    public void removeStudent(long id) {
        repository.deleteById(id);
    }

    public Collection<Student> getStudents() {
        return repository.findAll();
    }

    public List<Student> getStudentByAge(Integer age) {
        return repository.findAllById(age);
    }
}
