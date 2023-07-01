package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.DTO.inputDto.UserInputDTO;
import com.example.backendeindopdracht.DTO.outputDto.UserOutputDTO;
import com.example.backendeindopdracht.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    //POST
    @PostMapping("/add")
    public ResponseEntity<UserOutputDTO> addUser (@RequestBody UserInputDTO userInputDTO){
        UserOutputDTO addedUser = userService.addUser(userInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
    }

}
