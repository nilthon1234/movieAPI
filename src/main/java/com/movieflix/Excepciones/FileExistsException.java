package com.movieflix.Excepciones;

public class FileExistsException extends  RuntimeException{
    public FileExistsException(String message) {
        super(message);
    }
}
