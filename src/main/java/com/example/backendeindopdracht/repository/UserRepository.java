package com.example.backendeindopdracht.repository;

import com.example.backendeindopdracht.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
