package com.movieflix.Excepciones;

public class MovieNotFoundException extends  RuntimeException{
    public MovieNotFoundException(String message) {
        super(message);
    }
}
