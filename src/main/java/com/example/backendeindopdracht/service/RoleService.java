package com.example.backendeindopdracht.service;

import com.example.backendeindopdracht.DTO.inputDTO.RoleInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderOutputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RoleOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    //POST
    public RoleOutputDTO addRole (RoleInputDTO roleInputDTO){
        Role role = transferInputDtoRoleToRole(roleInputDTO);
        roleRepository.save(role);
        RoleOutputDTO roleOutputDTO = transferRoleToDTO(role);

        return roleOutputDTO;
    }

    //GET ALL
    public List<RoleOutputDTO> getAllRoles(){
        Iterable<Role> roles = roleRepository.findAll();
        List<RoleOutputDTO> roleOutputDTOList = new ArrayList<>();

        for (Role role : roles){
            roleOutputDTOList.add(transferRoleToDTO(role));
        }
        return roleOutputDTOList;
    }

    //GET BY ID
    public RoleOutputDTO getRoleById (Long id){
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()){
            throw new RecordNotFoundException("No role found with id: " + id);
        }
        Role role = optionalRole.get();
        return transferRoleToDTO(role);

    }

    //DELETE
    public void deleteRole(Long id){
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()){
            throw new RecordNotFoundException("No role found with id: " + id);
        }
        roleRepository.deleteById(id);
    }


    public Role transferInputDtoRoleToRole (RoleInputDTO roleInputDTO){
        Role role = new Role();

        role.setRoleName(roleInputDTO.getRoleName());
        role.setId(roleInputDTO.getId());



        return role;
    }

    public RoleOutputDTO transferRoleToDTO (Role role){
        RoleOutputDTO roleOutputDTO = new RoleOutputDTO();

        roleOutputDTO.setRoleName(role.getRoleName());
        roleOutputDTO.setId(role.getId());

        return roleOutputDTO;
    }

}
