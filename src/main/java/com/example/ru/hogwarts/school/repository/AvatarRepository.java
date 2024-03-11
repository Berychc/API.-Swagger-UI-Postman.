package com.example.ru.hogwarts.school.repository;

import com.example.ru.hogwarts.school.model.Avatar;
import com.example.ru.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Student findAvatar(Long id);
}
