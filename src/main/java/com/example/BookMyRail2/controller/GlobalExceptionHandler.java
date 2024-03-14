package com.example.BookMyRail2.controller;
import com.example.BookMyRail2.exception.CustomException;
import com.example.BookMyRail2.exception.ResourceNotFoundException;
import com.example.BookMyRail2.response.ExceptionResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exce)
    {
        Map<String, String> errors = new HashMap<>();

        exce.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(),err.getDefaultMessage());
        });

        return errors;
    }


//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
//    public Map<String, String> handleConstraintViolationException(ConstraintViolationException exce)
//    {
//        Map<String, String> errors = new HashMap<>();
//
//        Set<ConstraintViolation<?>> constraintViolations = exce.getConstraintViolations();
//
//        for (ConstraintViolation<?> violation : constraintViolations)
//        {
//            String fieldName = violation.getPropertyPath().toString();
//            String errorMessage = violation.getMessage();
//            errors.put(fieldName, errorMessage);
//        }
//        return errors;
//    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ExceptionResponse handleDataIntegrityViolationException(DataIntegrityViolationException exce)
    {
        return new ExceptionResponse(exce.getRootCause().getMessage(),HttpStatus.NOT_ACCEPTABLE.value());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleResourceNotFoundException(ResourceNotFoundException exce)
    {
        return new ExceptionResponse(exce.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exce)
    {
        return new ExceptionResponse(exce.getMessage(),HttpStatus.NOT_ACCEPTABLE.value());
    }

    @ExceptionHandler(ConcurrentModificationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleConcurrentModificationException(ConcurrentModificationException exce)
    {
        return new ExceptionResponse(exce.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ExceptionResponse handleCustomException(CustomException exce)
    {
        return new ExceptionResponse(exce.getMessage(),HttpStatus.NOT_ACCEPTABLE.value());
    }


    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMissingPathVariableException(MissingPathVariableException exce)
    {
        return new ExceptionResponse(exce.getMessage(),HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ExceptionResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exce)
    {
        return new ExceptionResponse(exce.getMessage(),HttpStatus.NOT_ACCEPTABLE.value());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException exce)
    {
        return new ExceptionResponse(exce.getMessage(),HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleEntityNotFoundException(EntityNotFoundException exce)
    {
        return new ExceptionResponse(exce.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNoResourceFoundException(NoResourceFoundException exce)
    {
        return new ExceptionResponse(exce.getMessage(),HttpStatus.NOT_FOUND.value());
    }
}
