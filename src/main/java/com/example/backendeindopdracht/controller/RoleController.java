package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.DTO.inputDTO.RoleInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RoleOutputDTO;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.RoleRepository;
import com.example.backendeindopdracht.service.RoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {


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
    public ResponseEntity<?> updateRole(@PathVariable Long id, @Valid @RequestBody RoleInputDTO roleInputDTO) {
        ResponseEntity<?> responseEntity = roleService.updateRole(roleInputDTO, id);

        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            // Role not found, return a 404 response with the custom error message
            String errorMessage = (String) responseEntity.getBody();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } else {
            // Role updated successfully or other successful response
            return ResponseEntity.ok(responseEntity.getBody());
        }
    }
//    public ResponseEntity<RoleOutputDTO> updateRole (@PathVariable Long id, @Valid @RequestBody RoleInputDTO roleInputDTO){
//        return ResponseEntity.ok().body(roleService.updateRole(roleInputDTO, id));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
//    public ResponseEntity<Role> deleteRole(@PathVariable long id){
//
//        var user = roleService.deleteRole(id);
//        return ResponseEntity.status(HttpStatus.OK).body(user);
//        roleRepository.deleteById(id);
//        return ResponseEntity.noContent().build();


}
