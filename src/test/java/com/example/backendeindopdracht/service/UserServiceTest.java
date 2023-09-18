package com.example.backendeindopdracht.service;

import com.example.backendeindopdracht.DTO.inputDTO.UserInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.UserOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.repository.RoleRepository;
import com.example.backendeindopdracht.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldAddUser() {
        //arrange
        //test kun je me horen?
        //heb je een ethernet kabel ja
        UserInputDTO userInputDTO = new UserInputDTO();

        userInputDTO.setFirstName("Lizzy");
        userInputDTO.setLastName("Telford");
        userInputDTO.setPassword("password");
        userInputDTO.setEmail("lizzytelford@gmail.com");
        userInputDTO.setRoleid(3L);



        Optional<Role> optionalRole = roleRepository.findById(userInputDTO.getRoleid());
        if (optionalRole.isEmpty()){
            throw new RecordNotFoundException("No role found with id: " + userInputDTO.getRoleid());
        }

        userInputDTO.setRoleid(optionalRole.get().getId());



        //Role role = new Role(678L, "customer");
        userInputDTO.setRoleid(optionalRole.get().getId());
       // Mockito.when(roleRepository.findById(3L)).thenReturn(Optional.of(optionalRole));
        //Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        //act
        UserOutputDTO result = userService.addUser(userInputDTO);

        //assert
        assertNotNull(result);
        assertEquals("Lizzy", result.getFirstName());
        assertEquals("Telford", result.getLastName());
        assertEquals(3L, result.getRoleId());
    }


}



