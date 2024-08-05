package com.movieflix.Excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // indicamos que esta clase actuara como un  controller  de excepciones globales
public class GlobalExceptionHandler {
    @ExceptionHandler(MovieNotFoundException.class)
   public ProblemDetail handleMovieNotFoundException(MovieNotFoundException ex){
       return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
   }

   @ExceptionHandler(FileExistsException.class)
    public ProblemDetail handleFileExistsException(FileExistsException ex){
        return  ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

   }

    @ExceptionHandler(EmptyFileExeceotion.class)
    public ProblemDetail handleFileExistsException(EmptyFileExeceotion ex){
        return  ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

    }

}
