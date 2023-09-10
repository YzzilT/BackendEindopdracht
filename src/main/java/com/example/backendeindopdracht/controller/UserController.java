package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.DTO.inputDTO.OrderInputDTO;
import com.example.backendeindopdracht.DTO.inputDTO.UserInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderOutputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.UserOutputDTO;
import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.repository.OrderRepository;
import com.example.backendeindopdracht.repository.RoleRepository;
import com.example.backendeindopdracht.repository.UserRepository;
import com.example.backendeindopdracht.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrderRepository orderRepository;
    @GetMapping()
    public ResponseEntity<Iterable<User>> getAllUser (){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser (@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(id).get());
    }

    //POST
    /*
    @PostMapping()
    public ResponseEntity<User> addUser (@RequestBody User user){
        var role = roleRepository.findById((long) user.getRoleid()).get();
        user.setRole(role);
        user = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    } */

    @PostMapping("")
    public ResponseEntity<UserOutputDTO> addUser (@RequestBody UserInputDTO userInputDTO){
        UserOutputDTO addedUser = userService.addUser(userInputDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
    }

    @PutMapping()
    public ResponseEntity<User> getUser (@RequestBody User user){
        user = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserOutputDTO> deleteUser (@PathVariable long id){
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
