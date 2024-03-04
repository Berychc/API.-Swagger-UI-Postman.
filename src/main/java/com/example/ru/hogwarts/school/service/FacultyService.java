package com.example.ru.hogwarts.school.service;

import com.example.ru.hogwarts.school.model.Faculty;

import com.example.ru.hogwarts.school.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;


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
        return repository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public void removeFaculty(long id) {
        repository.deleteById(id);
    }


    public Collection<Faculty> getFaculties() {
        return repository.findAll();

    }
}
