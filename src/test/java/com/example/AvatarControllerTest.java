package com.example;

import com.example.ru.hogwarts.school.controller.AvatarController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AvatarControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AvatarController avatarController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testUploadAvatar() {
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/avatar", null, String.class);
        assertNotNull(response);
    }

}
