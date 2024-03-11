package com.example.ru.hogwarts.school.repository;

import com.example.ru.hogwarts.school.model.Avatar;
import com.example.ru.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);

    Student findAvatar(Long studentId);
}
