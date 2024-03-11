package com.example.ru.hogwarts.school.controller;

import com.example.ru.hogwarts.school.service.AvatarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService service;

    public AvatarController(AvatarService service) {
        this.service = service;
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id,
                                               @RequestParam MultipartFile avatarFile) throws IOException {
        service.uploadAvatar(id, avatarFile);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        return service.readImagePreviewFileFromDb(id);
    }

    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        service.readImagePreviewFileFromLocal(id, response);
    }

}
