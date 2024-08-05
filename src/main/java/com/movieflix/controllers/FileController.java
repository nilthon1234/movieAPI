package com.movieflix.controllers;

import com.movieflix.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Order(6)

@RestController
@RequestMapping("/file/")


public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @Value("${project.poster}")
    private String path;

    //MultipartFile file en el key del posman colocamos file para exportar el archivo
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException{

        String uploadedFileName = fileService.uploadFile(path, file);
        return ResponseEntity.ok("Archivo subido : " + uploadedFileName);
    }

    @GetMapping(value = "/{fileName}")
    public void  serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException{
        InputStream resourceFile = fileService.getResourceFile(path, fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resourceFile, response.getOutputStream());
    }

}
