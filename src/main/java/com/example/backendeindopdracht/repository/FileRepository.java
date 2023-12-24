package com.example.backendeindopdracht.repository;

import com.example.backendeindopdracht.model.File;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FileRepository extends CrudRepository<File, Long> {

  Optional <File> findByFileName(String fileName);
}
