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

    @PostMapping
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
//   FileRepository fileRepository;
//   RepairRepository repairRepository;
//
   private final FileService fileService;
//
//    @PostMapping("/single/upload/{repairid}")
//    public ResponseEntity<String> singleFileUpload (@RequestParam ("file") MultipartFile file, @PathVariable long repairid) throws IOException {
//
//        var x= fileService.uploadSingleFile(file, repairid);
//
//        ResponseEntity.noContent().header("Location", "/download/" + x.getId()).build();
//        return ResponseEntity.ok("File uploaded, the id is:" +  x.getId());
//    }
//
////   @GetMapping("/download/{fileId}")
////    public ResponseEntity<byte[]> singleFileDownload (@PathVariable Long fileId) {
////
////        File file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException());
////        byte[] docFile = file.getDocFile();
////
////        if (docFile == null){
////            throw new RuntimeException("there is no file yet");
////        }
////
////       HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.IMAGE_PNG);
////        headers.setContentDispositionFormData("attachment", "file" + file.getFileName() + ".PNG");
////        headers.setContentLength(docFile.length);
////
////        return new ResponseEntity<>(docFile, headers, HttpStatus.OK);
////
////
////   }
////   @GetMapping("/download/{fileId}")
////   public ResponseEntity<File> singleFileDownload(@PathVariable Long fileId) {
////        var bytes = fileService.singleFileDownload(fileId);
////        return ResponseEntity.ok(bytes);
////   }
//
//
}
//
