package com.mindmatch.pagamento.controller.handlers;

import com.mindmatch.pagamento.controller.handlers.dto.CustomErrorDTO;
import com.mindmatch.pagamento.controller.handlers.dto.ValidationErrorDTO;
import com.mindmatch.pagamento.service.exceptions.DatabaseException;
import com.mindmatch.pagamento.service.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    //   @ExceptionHandler - definimos qual a exceção que iremos tratar
    @ExceptionHandler(ResourceNotFoundException.class) //nossa classe
    public ResponseEntity<CustomErrorDTO> handleResourceNotFound(ResourceNotFoundException e,
                                                                 HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND; //404
        CustomErrorDTO err = new CustomErrorDTO(Instant.now(), status.value(),
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDTO> methodArgumentNotValidation(MethodArgumentNotValidException e,
                                                                      HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationErrorDTO err = new ValidationErrorDTO(Instant.now(), status.value(),
                "Dados inválidos", request.getRequestURI());

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomErrorDTO> handleDatabase(DatabaseException e,
                                                         HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        // ou
        // HttpStatus status = HttpStatus.CONFLICT;
        CustomErrorDTO err = new CustomErrorDTO(Instant.now(), status.value(),
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
