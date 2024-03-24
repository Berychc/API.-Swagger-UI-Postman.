package com.example.ru.hogwarts.school.service;

import com.example.ru.hogwarts.school.model.Faculty;

import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.repository.FacultyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public Faculty readFaculty(long id) {
        return repository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found"));
    }

    public Faculty editFaculty(Faculty faculty) {
        if (repository.existsById(faculty.getId())) {
            return repository.save(faculty);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found");
        }
    }

    public void removeFaculty(long id) {
        repository.deleteById(id);
    }


    public Collection<Faculty> getFaculties() {
        return repository.findAll();
    }


    public List<Faculty> searchFaculties(String facultyName, String color) {
        return repository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(facultyName, color);
    }

    public List<Student> getFacultyStudents(Long facultyId) {
        Faculty faculty = repository.findById(facultyId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Faculty not found with id: " + facultyId));

        return faculty.getStudents();
    }
}
