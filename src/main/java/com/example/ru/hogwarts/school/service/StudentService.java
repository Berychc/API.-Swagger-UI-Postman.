package com.example.ru.hogwarts.school.service;

import com.example.ru.hogwarts.school.model.Avatar;
import com.example.ru.hogwarts.school.model.Faculty;
import com.example.ru.hogwarts.school.model.Student;
import com.example.ru.hogwarts.school.repository.AvatarRepository;
import com.example.ru.hogwarts.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class StudentService {

    @Value("${avatars.dir.path}")
    private String avatarsDir;

    private final StudentRepository repository;
    private final AvatarRepository avatarRepository;

    public StudentService(StudentRepository repository, AvatarRepository avatarRepository) {
        this.repository = repository;
        this.avatarRepository = avatarRepository;
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public Student readStudent(long id) {
        return repository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public Student editStudent(Student student) {
        if (repository.existsById(student.getId())) {
            return repository.save(student);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
    }

    public void removeStudent(long id) {
        repository.deleteById(id);
    }

    public Collection<Student> getStudents() {
        return repository.findAll();
    }

    public List<Student> getStudentByAge(Integer age) {
        return repository.findAllByAge(age);
    }

    public List<Student> getStudentsByAgeBetween(Integer min, Integer max) {
        return repository.findByAgeBetween(min, max);
    }

    public Faculty getStudentFaculty(long studentId) {
        Optional<Student> studentOptional = repository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return student.getFaculty();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with id: "
                    + studentId);
        }
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = avatarRepository.findAvatar(studentId);

        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);

        if (avatarFile.getSize() > 1024 * 300) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is too big!");
        }
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private Avatar findAvatar(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElseThrow();
    }

    public ResponseEntity<byte[]> readImagePreviewFileFromDb(Long studentId) {
        Avatar avatar = findAvatar(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    public void readImagePreviewFileFromLocal(Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try(InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }
}
