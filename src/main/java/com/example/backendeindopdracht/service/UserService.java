package com.example.backendeindopdracht.service;

import com.example.backendeindopdracht.DTO.inputDto.UserInputDTO;
import com.example.backendeindopdracht.DTO.outputDto.UserOutputDTO;
import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


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
        user.setRole(userInputDTO.getRole());

        return user;
    }

    public UserOutputDTO transferUserToDTO(User user){
        UserOutputDTO userOutputDTO = new UserOutputDTO();

        userOutputDTO.setId(user.getId());
        userOutputDTO.setFirstName(user.getFirstName());
        userOutputDTO.setLastName(user.getLastName());
        userOutputDTO.setPassword(user.getPassword());
        userOutputDTO.setEmail(user.getEmail());
        userOutputDTO.setRole(user.getRole());

        return userOutputDTO;
    }
}
