package com.example.backendeindopdracht.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Entity
@Getter
@Setter
public class FileService {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;

    @Lob
    private byte[] docFile;
}
