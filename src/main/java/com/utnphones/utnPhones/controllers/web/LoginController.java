package com.utnphones.utnPhones.controllers.web;

import com.utnphones.utnPhones.controllers.PersonController;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.dto.LoginRequestDto;
import com.utnphones.utnPhones.exceptions.InvalidLoginException;
import com.utnphones.utnPhones.exceptions.UserNotfoundException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/")
public class LoginController {
    private SessionManager sessionManager;
    private PersonController personController;

    @Autowired
    public LoginController(SessionManager sessionManager, PersonController personController) {
        this.sessionManager = sessionManager;
        this.personController = personController;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) throws InvalidLoginException, ValidationException, NoSuchAlgorithmException {
        ResponseEntity<?> response;
        try {
            Person person = personController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
            String token = sessionManager.createSession(person);
            response = ResponseEntity.ok().headers(createHeaders(token)).build();
        } catch (UserNotfoundException e) {
            throw new InvalidLoginException();
        }
        return response;
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        sessionManager.removeSession(token);
        return ResponseEntity.ok().build();
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        return responseHeaders;
    }
}
