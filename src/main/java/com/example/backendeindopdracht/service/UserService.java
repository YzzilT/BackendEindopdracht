package com.example.backendeindopdracht.service;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.backendeindopdracht.DTO.inputDTO.UserInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.UserOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.UserRepository;
import com.example.backendeindopdracht.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    //POST
    public UserOutputDTO addUser(UserInputDTO userInputDTO) throws Exception {
        User user = transferInputDtoUserToUser(userInputDTO);
        Long roleId = userInputDTO.getRoleid();


        Optional<Role> optionalRole = roleRepository.findById(roleId);
//        https://www.baeldung.com/exception-handling-for-rest-with-spring
        user.setRole(optionalRole.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found")));
        var hashedPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return transferUserToDTO(user);

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

        User user = optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found"));
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

        userRepository.deleteById(id);
        return optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found"));
    }

    public User transferInputDtoUserToUser (UserInputDTO userInputDTO){

        User user = new User();

        user.setId(userInputDTO.getId());
        user.setFirstName(userInputDTO.getFirstName());
        user.setLastName(userInputDTO.getLastName());
        user.setPassword(userInputDTO.getPassword());
        user.setEmail(userInputDTO.getEmail());

        Optional<Role> optionalRole = roleRepository.findById(userInputDTO.getRoleid());
        user.setRole(optionalRole.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found")));


        return user;
    }

    public UserOutputDTO transferUserToDTO(User user){

        UserOutputDTO userOutputDTO = new UserOutputDTO();

        userOutputDTO.setId(user.getId());
        userOutputDTO.setFirstName(user.getFirstName());
        userOutputDTO.setLastName(user.getLastName());
        userOutputDTO.setPassword(user.getPassword());
        userOutputDTO.setEmail(user.getEmail());

        if (user.getRole() != null) {
            userOutputDTO.setRoleId(user.getRole().getId());
        } else {
            throw new RecordNotFoundException("User role is null");
        }


        return userOutputDTO;
    }
}
