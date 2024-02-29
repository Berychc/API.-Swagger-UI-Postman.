package com.example.ru.hogwarts.school.service;

import com.example.ru.hogwarts.school.model.Student;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

@Service
public class StudentService {
    private final HashMap<Long, Student> students = new HashMap<>();
    private long lastId = 0;

    public Student createStudent(Student student) {
        student.setId(lastId++);
        students.put(lastId, student);
        return student;
    }

    public Student readStudent(long id) {
        return students.get(id);
    }

    public Student editStudent(Student student) {
        students.put(student.getId(), student);
        return student;
    }

    public Student removeStudent(long id) {
        return students.remove(id);
    }

    public Collection<Student> getStudents() {
        return Collections.unmodifiableCollection(students.values());
    }
}
