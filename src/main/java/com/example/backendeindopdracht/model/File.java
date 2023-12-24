package com.example.backendeindopdracht.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "images")

public class File {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private long repairId;

        private String fileName;
        private String type;

        @Lob
        @Column(name= "docFile")
        private byte[] docFile;
}


