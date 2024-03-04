package com.example.ru.hogwarts.school.repository;

import com.example.ru.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository  extends JpaRepository<Student, Long> {
    List<Student> findAllById(Integer age);

    Object findByAge(Integer ageToSearch);
}
