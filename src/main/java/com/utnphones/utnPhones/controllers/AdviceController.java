package com.utnphones.utnPhones.controllers;


import com.utnphones.utnPhones.dto.ErrorResponseDto;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.InvalidLoginException;
import com.utnphones.utnPhones.exceptions.ParseDateException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.utnphones.utnPhones.utils.Constants.CLIENT_NOT_EXISTS_MESSAGE;
import static com.utnphones.utnPhones.utils.Constants.INVALID_DATE_FORMAT_MESSAGE;
import static com.utnphones.utnPhones.utils.Constants.INVALID_LOGIN_MESSAGE;
import static com.utnphones.utnPhones.utils.Constants.PHONELINE_NOT_EXISTS_MESSAGE;


@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {
//TODO agregar todas

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidLoginException.class)
    public ErrorResponseDto handleLoginException(InvalidLoginException exc) {
        return new ErrorResponseDto(1, INVALID_LOGIN_MESSAGE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponseDto handleValidationException(ValidationException exc) {
        return new ErrorResponseDto(2, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ClientNotFoundException.class)
    public ErrorResponseDto handleUserNotExists() {
        return new ErrorResponseDto(3, CLIENT_NOT_EXISTS_MESSAGE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PhoneLineNotFoundException.class)
    public ErrorResponseDto handlePhoneLineNotExists() {
        return new ErrorResponseDto(3, PHONELINE_NOT_EXISTS_MESSAGE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseDateException.class)
    public ErrorResponseDto handleParseException() {
        return new ErrorResponseDto(4, INVALID_DATE_FORMAT_MESSAGE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientIsAlreadyDeletedException.class)
    public ErrorResponseDto handleClientIsAlreadyDeletedException() {
        return new ErrorResponseDto(4, CLIENT_NOT_EXISTS_MESSAGE);
    }



}
