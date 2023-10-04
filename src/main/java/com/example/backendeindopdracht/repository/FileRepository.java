package com.example.backendeindopdracht.repository;

import com.example.backendeindopdracht.model.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {
    File findByFileName(String fileName);
}
