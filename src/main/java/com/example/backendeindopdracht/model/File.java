package com.example.backendeindopdracht.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
@Table(name = "files")

public class File {

        @Id
        @GeneratedValue
        private Long id;

        private String fileName;

        @Lob
        private byte[] docFile;
}


