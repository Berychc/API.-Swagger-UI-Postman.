package com.example.ru.hogwarts.school.service;

import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.model.Student;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long lastId = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(lastId++);
        faculties.put(lastId, faculty);
        return faculty;
    }

    public Faculty readFaculty(long id) {
        return faculties.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty removeFaculty(long id) {
        return faculties.remove(id);
    }


    public Collection<Faculty> getFaculties() {
        return Collections.unmodifiableCollection(faculties.values());
    }
}
