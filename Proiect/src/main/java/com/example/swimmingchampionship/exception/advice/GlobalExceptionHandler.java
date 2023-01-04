package com.example.swimmingchampionship.exception.advice;

import com.example.swimmingchampionship.exception.NoSemifinalException;
import com.example.swimmingchampionship.exception.NotFoundException;
import com.example.swimmingchampionship.exception.WrongNumberException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({NotFoundException.class, WrongNumberException.class, NoSemifinalException.class})
    public ResponseEntity<String> handle(RuntimeException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage() + " at " + LocalDateTime.now());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleParamsValidation(ConstraintViolationException e){
        return ResponseEntity.badRequest().body(
                e.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("\n"))
        );
    }

    @ExceptionHandler
    public ResponseEntity<String> handleBodyValidation(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(
                e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n"))
        );
    }

    @ExceptionHandler
    public ResponseEntity<String> handleBodyValidation(MethodArgumentTypeMismatchException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
