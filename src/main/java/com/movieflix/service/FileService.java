package com.movieflix.service;

import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Order(4)// aremos el metodo de cargar el archivo


public interface FileService {
    //método que se utiliza para cargar (subir) un archivo al servidor
    String uploadFile(String path, MultipartFile file) throws IOException;

    //este metodo sera utilizado para obtener un flujo de entrada
    InputStream getResourceFile(String path, String filename) throws FileNotFoundException;


}
