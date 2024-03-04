package com.example.ru.hogwarts.school.repository;

import com.example.ru.hogwarts.school.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
