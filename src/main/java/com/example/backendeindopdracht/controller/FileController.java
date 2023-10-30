package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.model.File;
import com.example.backendeindopdracht.repository.FileRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class FileController {

   FileRepository fileRepository;

    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @PostMapping("/single/upload")
    public ResponseEntity<String> singleFileUpload (@RequestParam ("file") MultipartFile file) throws IOException {
        File uploadFile = new File();
        uploadFile.setFileName("filename");
        uploadFile.setDocFile(file.getBytes());

        var x = fileRepository.save(uploadFile);
        return ResponseEntity.ok("File uploaded, the id is:" +  x.getId());

    }

   @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> singleFileDownload (@PathVariable Long fileId) {

        File file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException());
        byte[] docFile = file.getDocFile();

        if (docFile == null){
            throw new RuntimeException("there is no file yet");
        }

       HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentDispositionFormData("attachment", "file" + file.getFileName() + ".PNG");
        headers.setContentLength(docFile.length);

        return new ResponseEntity<>(docFile, headers, HttpStatus.OK);


   }
}
