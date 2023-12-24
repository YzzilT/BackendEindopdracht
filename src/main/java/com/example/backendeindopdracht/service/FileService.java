package com.example.backendeindopdracht.service;

import com.example.backendeindopdracht.model.File;
import com.example.backendeindopdracht.repository.FileRepository;
import com.example.backendeindopdracht.util.ImageUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Getter
@Setter
@AllArgsConstructor
@Transactional

public class FileService {


private final FileRepository fileRepository;

public String uploadImage(MultipartFile file, long repairid) throws IOException {
    var myfile = File.builder()
            .fileName(file.getOriginalFilename())
            .type(file.getContentType())
            .docFile(ImageUtil.compressImage(file.getBytes())).build();

    myfile.setRepairId(repairid);
    File image = fileRepository.save(myfile);
    if (image!=null){
        return "file uploaded succesfully : " + file.getOriginalFilename();
    }
    return null;
}


public byte[] downloadImage(String fileName){
    Optional<File> dbImage = fileRepository.findByFileName(fileName);
    byte[] images = ImageUtil.decompressImage(dbImage.get().getDocFile());
    return images;




    }


}
