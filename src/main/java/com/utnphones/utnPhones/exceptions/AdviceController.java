package com.utnphones.utnPhones.exceptions;


import com.utnphones.utnPhones.dto.ErrorResponseDto;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.InvalidLoginException;
import com.utnphones.utnPhones.exceptions.InvoiceNotFoundException;
import com.utnphones.utnPhones.exceptions.ParseDateException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.exceptions.UnauthorizedAccessException;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.ParseException;

import static com.utnphones.utnPhones.utils.Constants.CITY_NOT_FOUND;
import static com.utnphones.utnPhones.utils.Constants.CLIENT_NOT_EXISTS_MESSAGE;
import static com.utnphones.utnPhones.utils.Constants.INVALID_DATE_FORMAT_MESSAGE;
import static com.utnphones.utnPhones.utils.Constants.INVALID_FORMAT_MESSAGE;
import static com.utnphones.utnPhones.utils.Constants.INVALID_LOGIN_MESSAGE;
import static com.utnphones.utnPhones.utils.Constants.INVOICE_NOT_EXISTS;
import static com.utnphones.utnPhones.utils.Constants.PHONELINE_NOT_EXISTS_MESSAGE;
import static com.utnphones.utnPhones.utils.Constants.UNAUTHORIZED_ACCESS_MESSAGE;
import static com.utnphones.utnPhones.utils.Constants.USER_NOT_LOGGED_MESSAGE;


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
        return new ErrorResponseDto(4, PHONELINE_NOT_EXISTS_MESSAGE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseDateException.class)
    public ErrorResponseDto handleParseDateException() {
        return new ErrorResponseDto(5, INVALID_DATE_FORMAT_MESSAGE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseException.class)
    public ErrorResponseDto handleParseException() {
        return new ErrorResponseDto(5, INVALID_FORMAT_MESSAGE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientIsAlreadyDeletedException.class)
    public ErrorResponseDto handleClientIsAlreadyDeletedException() {
        return new ErrorResponseDto(6, CLIENT_NOT_EXISTS_MESSAGE);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserNotLoggedException.class)
    public ErrorResponseDto handleUserNotLoggedExceptionException() {
        return new ErrorResponseDto(7, USER_NOT_LOGGED_MESSAGE);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ErrorResponseDto handleUnauthorizedAccessException(){
        return new ErrorResponseDto(8, UNAUTHORIZED_ACCESS_MESSAGE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CityNotFoundException.class)
    public ErrorResponseDto handleCityNotFoundException(){
        return new ErrorResponseDto(6, CITY_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponseDto handleUserNException(ConstraintViolationException ex)
    {
        return new ErrorResponseDto(6, ex.getCause().getMessage());
    } // todo ver

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvoiceNotFoundException.class)
    public ErrorResponseDto handleInvoiceNotFoundException(ConstraintViolationException ex)
    {
        return new ErrorResponseDto(4, INVOICE_NOT_EXISTS);
    }
    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LineTypeNotFoundException.class)
    public ErrorResponseDto handleLineTypeNotFoundException(LineTypeNotFoundException ex)
    {
        return new ErrorResponseDto(9, "Ese tipo de linea no existe");
    }*/
}
