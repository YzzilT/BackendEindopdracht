package com.example.backendeindopdracht.controller;
import com.example.backendeindopdracht.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/image")
public class FileController {
    private final FileService fileService;

    @PostMapping("/{repairid}")
    public ResponseEntity<?>uploadImage(@RequestParam ("docFile") MultipartFile file,@PathVariable long repairid) throws IOException {
        String uploadImage = fileService.uploadImage(file,repairid);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte [] imageData = fileService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}

