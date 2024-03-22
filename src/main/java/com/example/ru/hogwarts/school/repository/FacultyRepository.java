package com.example.ru.hogwarts.school.repository;

import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findAllByColorIgnoreCase(String color);

    List<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String facultyName,
                                                                       String color);
}
