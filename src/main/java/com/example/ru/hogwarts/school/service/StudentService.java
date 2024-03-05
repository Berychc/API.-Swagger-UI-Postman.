package com.example.ru.hogwarts.school.service;

import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

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
        return repository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public Student editStudent(Student student) {
        if (repository.existsById(student.getId())) {
            return repository.save(student);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
    }

    public void removeStudent(long id) {
        repository.deleteById(id);
    }

    public Collection<Student> getStudents() {
        return repository.findAll();
    }

    public List<Student> getStudentByAge(Integer age) {
        return repository.findAllByAge(age);
    }
}
