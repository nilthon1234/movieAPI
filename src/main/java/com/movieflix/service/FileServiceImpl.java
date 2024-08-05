package com.movieflix.service;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Order(5)

@Service
public class FileServiceImpl implements FileService{


    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        //obtener el nombre del archivo
        String fileName = file.getOriginalFilename();

        //para obtener la ruta del archivo
        String filePath = path + File.separator + fileName;

        //crear objeto de archivo
        File f = new File(path);
        if(!f.exists()){
            f.mkdir(); // Este método se utiliza para crear el directorio representado por el objeto File
        }

        //copiar el archivo o subir el archivo a la ruta

        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {

        String filePath = path + File.separator + filename;
        return new FileInputStream(filePath);


    }
}
