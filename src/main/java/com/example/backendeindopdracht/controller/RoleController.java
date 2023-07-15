package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository roleRepository;
    @GetMapping()
    public ResponseEntity<Iterable<Role>> getAllRoles(){
        return ResponseEntity.status(HttpStatus.OK).body(roleRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole (@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(roleRepository.findById(id).get());
    }

    //POST
    @PostMapping()
    public ResponseEntity<Role> addRole (@RequestBody Role role){
        role = roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @PutMapping()
    public ResponseEntity<Role> getRole (@RequestBody Role role){
        role = roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable long id){
        roleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
