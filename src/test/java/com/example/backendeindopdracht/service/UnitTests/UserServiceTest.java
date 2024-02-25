package com.example.backendeindopdracht.service.UnitTests;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.backendeindopdracht.DTO.inputDTO.UserInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.UserOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.repository.RoleRepository;
import com.example.backendeindopdracht.repository.UserRepository;
import com.example.backendeindopdracht.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockBean
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;




    @Test
    void shouldAddUser() throws Exception {

        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setId(1L);
        userInputDTO.setFirstName("Lizzy");
        userInputDTO.setLastName("Telford");
        userInputDTO.setPassword("password");
        userInputDTO.setEmail("lizzytelford@gmail.com");
        userInputDTO.setRoleid(1L);

        Role role = new Role();
        role.setId(1L);
        role.setRoleName("ROLE_frontdesk");

        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

       User savedUser = new User();
       savedUser.setId(1L);



        UserOutputDTO addedUser = userService.addUser(userInputDTO);


        assertEquals(userInputDTO.getId(), addedUser.getId());
        assertEquals(userInputDTO.getFirstName(), addedUser.getFirstName());
        assertEquals(userInputDTO.getLastName(),addedUser.getLastName());
        assertTrue(BCrypt.verifyer().verify(userInputDTO.getPassword().toCharArray(), addedUser.getPassword()).verified);
        assertEquals(userInputDTO.getEmail(), addedUser.getEmail());
        assertEquals(userInputDTO.getRoleid(), addedUser.getRoleId());
    }

    @Test
    void shouldNotReturnUserWhenRoleIsNull(){
        UserInputDTO userInputDTO = new UserInputDTO();

        assertThrows(ResponseStatusException.class, () -> userService.addUser(userInputDTO));
    }

    @Test
    void shouldNotReturnUserWhenRoleNotFound(){
        Long roleId = 1L;
        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setRoleid(roleId);

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            userService.addUser(userInputDTO);
        });
    }

    @Test
    void shouldGetAllUsers(){


        Role role1 = new Role();
        role1.setId(1L);
        role1.setRoleName("ROLE_frontdesk");


        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Lizzy");
        user1.setLastName("Telford");
        user1.setPassword("lizzytelford");
        user1.setEmail("lizzytelford@gmail.com");
        user1.setRole(role1);


        Role role2 = new Role();
        role2.setId(2L);
        role2.setRoleName("ROLE_warehouse");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("John");
        user2.setLastName("Doe");
        user2.setPassword("lizzytelford");
        user2.setEmail("johndoe@gmail.com");
        user2.setRole(role2);


        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        when(userRepository.findAll()).thenReturn(userList);


        List<UserOutputDTO> userOutputDTOList = userService.getAllUsers();


        assertEquals(2, userOutputDTOList.size());

        UserOutputDTO userOutputDTO1 = userOutputDTOList.get(0);
        assertEquals(1L, userOutputDTO1.getId());
        assertEquals("Lizzy", userOutputDTO1.getFirstName());
        assertEquals("Telford", userOutputDTO1.getLastName());
        assertEquals("lizzytelford", userOutputDTO1.getPassword());
        assertEquals("lizzytelford@gmail.com", userOutputDTO1.getEmail());




        UserOutputDTO userOutputDTO2 = userOutputDTOList.get(1);
        assertEquals(2L, userOutputDTO2.getRoleId());
        assertEquals("John",  userOutputDTO2.getFirstName());
        assertEquals("Doe",  userOutputDTO2.getLastName());
        assertEquals("lizzytelford", userOutputDTO2.getPassword());
        assertEquals("johndoe@gmail.com",  userOutputDTO2.getEmail());

    }

    @Test
    void shouldGetUserById(){
        //arrange

    Long userId = 1L;

        User user = new User();
        user.setId(userId);
        user.setFirstName("Lizzy");
        user.setLastName("Telford");
        user.setPassword("lizzytelford");
        user.setEmail("lizzytelford@gmail.com");
        user.setRole(new Role());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //act
        UserOutputDTO userOutputDTO = userService.getUserById(userId);

        //assert
        assertEquals(userId, userOutputDTO.getId());
        assertEquals("Lizzy", userOutputDTO.getFirstName());
        assertEquals("Telford", userOutputDTO.getLastName());
        assertEquals("lizzytelford", userOutputDTO.getPassword());
        assertEquals("lizzytelford@gmail.com", userOutputDTO.getEmail());


    }

    @Test
    void shouldThrowRecordNotFoundExceptionWhenUserNotFound(){

        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());


        assertThrows(ResponseStatusException.class, () -> userService.getUserById(userId));

    }

    @Test
    void shouldDeleteUserWhenUserFound(){
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User user1 = userService.deleteUser(userId);

        assertEquals(userId, user1.getId());

    }


    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser(){
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.deleteUser(userId));

    }

    @Test
    void shouldUpdateUser(){

        Long userId = 1L;

        UserInputDTO updatedUserInputDTO = new UserInputDTO();
        updatedUserInputDTO.setFirstName("Rizzy");
        updatedUserInputDTO.setLastName("Relford");
        updatedUserInputDTO.setPassword("rizzyrelford");
        updatedUserInputDTO.setEmail("rizzyrelford@hotmail.com");
        updatedUserInputDTO.setRoleid(2L);

        Role role = new Role();
        role.setId(2L);
        role.setRoleName("ROLE_warehouse");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstName("Lizzy");
        existingUser.setLastName("Telford");
        existingUser.setPassword("lizzytelford");
        existingUser.setEmail("lizzytelford@hotmail.com");


        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        when(roleRepository.findById(2L)).thenReturn(Optional.of(role));

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User updatedUser = invocation.getArgument(0);

            return updatedUser;
        });

        UserOutputDTO updatedUserOutputDTO = userService.updateUser(updatedUserInputDTO, userId);

        assertEquals(userId, updatedUserOutputDTO.getId());
        assertEquals(updatedUserInputDTO.getFirstName(), updatedUserOutputDTO.getFirstName());
        assertEquals(updatedUserInputDTO.getLastName(), updatedUserOutputDTO.getLastName());
        assertEquals(updatedUserInputDTO.getPassword(), updatedUserOutputDTO.getPassword());
        assertEquals(updatedUserInputDTO.getEmail(), updatedUserOutputDTO.getEmail());
        assertEquals(updatedUserInputDTO.getRoleid(), updatedUserOutputDTO.getRoleId());
    }


    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentUser(){
        Long userId = 1L;

        UserInputDTO updatedUserInputDTO = new UserInputDTO();
        updatedUserInputDTO.setFirstName("Rizzy");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> userService.updateUser(updatedUserInputDTO, userId));

    }

    @Test
    void shouldTransferInputDtoUserToUser(){

        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setId(1L);
        userInputDTO.setFirstName("John");
        userInputDTO.setLastName("Doe");
        userInputDTO.setPassword("password123");
        userInputDTO.setEmail("john@example.com");
        userInputDTO.setRoleid(2L);

        Role role = new Role();
        role.setId(2L);
        role.setRoleName("user");

        when(roleRepository.findById(2L)).thenReturn(Optional.of(role));

        User user = userService.transferInputDtoUserToUser(userInputDTO);

        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("password123", user.getPassword());
        assertEquals("john@example.com", user.getEmail());
        assertEquals(2L, user.getRole().getId());
        assertEquals("user", user.getRole().getRoleName());


    }

    @Test
    void shouldThrowRecordNotFoundExceptionWhenRoleNotFound(){
        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setId(1L);
        userInputDTO.setRoleid(2L);

        when(roleRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.transferInputDtoUserToUser(userInputDTO));
    }

    @Test
    public void shouldTransferUserDtoWithRole(){
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("customer");

        User user = new User();
        user.setId(1L);
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("password");
        user.setEmail("janedoe@hotmail.com");
        user.setRole(role);


        UserOutputDTO userOutputDTO = userService.transferUserToDTO(user);

        assertEquals(1L, userOutputDTO.getId());
        assertEquals("Jane", userOutputDTO.getFirstName());
        assertEquals("Doe", userOutputDTO.getLastName());
        assertEquals("password", userOutputDTO.getPassword());
        assertEquals("janedoe@hotmail.com", userOutputDTO.getEmail());
        assertEquals(1L, userOutputDTO.getRoleId());
    }


    @Test
    public void shouldTransferUserDtoWithoutRole(){
        User user = new User();
        user.setId(2L);
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("password123");
        user.setEmail("janedoe@gmail.com");
        user.setRole(null);

        assertThrows(RecordNotFoundException.class, () -> {
            userService.transferUserToDTO(user);
        });

    }
}