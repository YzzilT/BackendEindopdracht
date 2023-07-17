package com.example.backendeindopdracht.service;

import com.example.backendeindopdracht.DTO.inputDTO.UserInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.UserOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.UserRepository;
import com.example.backendeindopdracht.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    //POST
    public UserOutputDTO addUser (UserInputDTO userInputDTO){
        User user = transferInputDtoUserToUser(userInputDTO);
        userRepository.save(user);

        return transferUserToDTO(user);
    }

    public User transferInputDtoUserToUser (UserInputDTO userInputDTO){
        User user = new User();

        user.setId(userInputDTO.getId());
        user.setFirstName(userInputDTO.getFirstName());
        user.setLastName(userInputDTO.getLastName());
        user.setPassword(userInputDTO.getPassword());
        user.setEmail(userInputDTO.getEmail());

        user.setRoleid(userInputDTO.getRoleid());
        Optional<Role> optionalRole = roleRepository.findById(user.getRoleid());

        if (optionalRole.isEmpty()) {
            throw new RecordNotFoundException("No role found with id: " + user.getRoleid());
        }
        else {
            user.setRole(optionalRole.get());
        }

        return user;
    }

    public UserOutputDTO transferUserToDTO(User user){
        UserOutputDTO userOutputDTO = new UserOutputDTO();

        userOutputDTO.setId(user.getId());
        userOutputDTO.setFirstName(user.getFirstName());
        userOutputDTO.setLastName(user.getLastName());
        userOutputDTO.setPassword(user.getPassword());
        userOutputDTO.setEmail(user.getEmail());
        userOutputDTO.setRoleId(user.getRole().getId());
        userOutputDTO.setRole(user.getRole());

        return userOutputDTO;
    }
}
