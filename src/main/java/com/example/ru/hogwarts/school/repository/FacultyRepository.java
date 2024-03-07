package com.example.ru.hogwarts.school.repository;

import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findAllByColorIgnoreCase(String color);

    List<Faculty> findByFacultyNameIgnoreCaseContainsOrColorIgnoreCase(String facultyName,
                                                                       String color);
}
