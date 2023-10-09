package com.example.backendeindopdracht.service;

import com.example.backendeindopdracht.DTO.inputDTO.ProductInputDTO;
import com.example.backendeindopdracht.DTO.inputDTO.UserInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.ProductOutputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.UserOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.UserRepository;
import com.example.backendeindopdracht.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    //POST
    public UserOutputDTO addUser(UserInputDTO userInputDTO) {
        User user = transferInputDtoUserToUser(userInputDTO);
        Long roleId = userInputDTO.getRoleid();
        if (roleId == null) {
            throw new IllegalArgumentException("Role ID is required");
        }

        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty()) {
            throw new RecordNotFoundException("No role found with id: " + userInputDTO.getRoleid());
        } else {

            user.setRole(optionalRole.get());
            userRepository.save(user);

            return transferUserToDTO(user);

        }
    }

    //GET ALL
    public List<UserOutputDTO> getAllUsers(){

        Iterable<User> users = userRepository.findAll();
        List<UserOutputDTO> userOutputDTOList = new ArrayList<>();


        for (User user : users){
            userOutputDTOList.add(transferUserToDTO(user));
        }
        return userOutputDTOList;
    }

    //GET BY ID
    public UserOutputDTO getUserById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new RecordNotFoundException("No user found with id: " + id);
        }
        User user = optionalUser.get();
        return transferUserToDTO(user);
    }



    //PUT
    public UserOutputDTO updateUser (UserInputDTO userInputDTO, Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new RecordNotFoundException("No user found with id " + id);
        } else {
            User updateUser = transferInputDtoUserToUser(userInputDTO);
            updateUser.setId(id);
            User updatedUser = userRepository.save(updateUser);

            return transferUserToDTO(updatedUser);
        }
    }

    //DELETE
    public User deleteUser (Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new RecordNotFoundException("No user was found with id: " + id);
        }
        userRepository.deleteById(id);
        return optionalUser.get();
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
//        userOutputDTO.setRoleId(user.getRoleid());
        if (user.getRole() != null) {
            userOutputDTO.setRoleId(user.getRoleid());
        } else {
            throw new RecordNotFoundException("User role is null");
        }


        return userOutputDTO;
    }
}
