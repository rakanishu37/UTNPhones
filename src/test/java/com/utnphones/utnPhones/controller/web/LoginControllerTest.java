package com.utnphones.utnPhones.controller.web;

import com.utnphones.utnPhones.controllers.PersonController;
import com.utnphones.utnPhones.controllers.web.BackOfficeController;
import com.utnphones.utnPhones.controllers.web.LoginController;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.dto.LoginRequestDto;
import com.utnphones.utnPhones.exceptions.InvalidLoginException;
import com.utnphones.utnPhones.exceptions.UserNotfoundException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import com.utnphones.utnPhones.session.SessionManager;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;

import static com.utnphones.utnPhones.utils.Constants.INVALID_USER_PASS_MESSAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoginControllerTest {
    @Mock
    private SessionManager sessionManager;
    @Mock
    private PersonController personController;

    private LoginController loginController;

    @Before
    public void setUp(){
        initMocks(this);
        loginController = new LoginController(sessionManager,personController);
    }

    @Test
    public void testLoginOk() throws ValidationException, NoSuchAlgorithmException, UserNotfoundException, InvalidLoginException {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username("test")
                .password("test")
                .build();

        Person person = Person.builder()
                .username(loginRequestDto.getUsername())
                .password(loginRequestDto.getPassword())
                .build();

        String token = "token";

        when(personController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword())).thenReturn(person);
        when(sessionManager.createSession(person)).thenReturn(token);

        ResponseEntity responseEntity = loginController.login(loginRequestDto);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(token,responseEntity.getHeaders().get("Authorization").get(0));
    }

    @Test(expected = InvalidLoginException.class)
    public void testLoginThenThrowInvalidLogin() throws ValidationException, NoSuchAlgorithmException, UserNotfoundException, InvalidLoginException {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username("test")
                .password("test")
                .build();
        when(personController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword())).thenThrow(new UserNotfoundException());
        loginController.login(loginRequestDto);
    }

    @Test(expected = ValidationException.class)
    public void testLoginThenWithInvalidUsernamePassword() throws ValidationException, NoSuchAlgorithmException, UserNotfoundException, InvalidLoginException {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username(null)
                .password(null)
                .build();
        when(personController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword())).thenThrow(new ValidationException(INVALID_USER_PASS_MESSAGE));
        loginController.login(loginRequestDto);
    }


    @Test
    public void testLogout(){
        String token = "token";
        doNothing().when(sessionManager).removeSession(token);
        ResponseEntity responseEntity = loginController.logout(token);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        verify(sessionManager,times(1)).removeSession(token);
    }
}
