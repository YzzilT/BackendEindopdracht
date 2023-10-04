package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.DTO.inputDTO.UserInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.UserOutputDTO;
import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.repository.OrderRepository;
import com.example.backendeindopdracht.repository.RoleRepository;
import com.example.backendeindopdracht.repository.UserRepository;
import com.example.backendeindopdracht.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping()
    public ResponseEntity<List<UserOutputDTO>> getAllUsers (){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutputDTO> getUserById (@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }


    @PostMapping("/add")

    public ResponseEntity<UserOutputDTO> addUser (@RequestBody UserInputDTO userInputDTO){
        UserOutputDTO addedUser = userService.addUser(userInputDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOutputDTO> updateUser (@PathVariable Long id, @Valid @RequestBody UserInputDTO userInputDTO){
        return ResponseEntity.ok().body(userService.updateUser(userInputDTO, id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser (@PathVariable long id){

        var user = userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
