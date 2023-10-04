package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.DTO.inputDTO.RoleInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RoleOutputDTO;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.RoleRepository;
import com.example.backendeindopdracht.service.RoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository roleRepository;
    private final RoleService roleService;
    @GetMapping()
    public ResponseEntity<List<RoleOutputDTO>> getAllRoles(){
        return ResponseEntity.ok().body(roleService.getAllRoles());

    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleOutputDTO> getRoleById (@PathVariable long id){
        return ResponseEntity.ok().body(roleService.getRoleById(id));
    }

    //POST
    @PostMapping()
    public ResponseEntity<RoleOutputDTO> addRole (@RequestBody RoleInputDTO roleInputDTO){
        RoleOutputDTO addedRole = roleService.addRole(roleInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleOutputDTO> updateRole (@PathVariable Long id, @Valid @RequestBody RoleInputDTO roleInputDTO){
        return ResponseEntity.ok().body(roleService.updateRole(roleInputDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable long id){

        var user = roleService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
//        roleRepository.deleteById(id);
//        return ResponseEntity.noContent().build();
    }

}
