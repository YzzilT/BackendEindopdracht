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
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldAddUser(){
        //arrange
        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setId(1L);
        userInputDTO.setFirstName("Lizzy");
        userInputDTO.setLastName("Telford");
        userInputDTO.setPassword("password");
        userInputDTO.setEmail("lizzytelford@gmail.com");
        userInputDTO.setRoleid(1L);

        Role role = new Role();
        role.setId(1L);
        role.setRoleName("customer");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        //act
        UserOutputDTO userOutputDTO = userService.addUser(userInputDTO);

        //assert
        assertNotNull(userOutputDTO);
        assertEquals(1L, userOutputDTO.getId());

        verify(userRepository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("Lizzy", savedUser.getFirstName());
    }

    @Test
    void shouldGetAllUsers(){
        //arrange

        Role role1 = new Role();
        role1.setId(1L);
        role1.setRoleName("customer");


        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Lizzy");
        user1.setLastName("Telford");
        user1.setPassword("lizzytelford");
        user1.setEmail("lizzytelford@gmail.com");
        user1.setRole(role1);


        Role role2 = new Role();
        role2.setId(2L);
        role2.setRoleName("admin");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Winston");
        user2.setLastName("Kat");
        user2.setPassword("winstonkat");
        user2.setEmail("winstonkat@gmail.com");
        user2.setRole(role2);


        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        //act
        List<UserOutputDTO> userOutputDTOList = userService.getAllUsers();

        //assert
        assertEquals(2, userOutputDTOList.size());

        UserOutputDTO userOutputDTO1 = userOutputDTOList.get(0);
        assertEquals(1L, userOutputDTO1.getId());
        assertEquals("Lizzy", userOutputDTO1.getFirstName());
        assertEquals("Telford", userOutputDTO1.getLastName());
        assertEquals("lizzytelford", userOutputDTO1.getPassword());
        assertEquals("lizzytelford@gmail.com", userOutputDTO1.getEmail());
        //rolename


        UserOutputDTO userOutputDTO2 = userOutputDTOList.get(1);
        assertEquals(2L,  userOutputDTO2.getRoleId());
        assertEquals("Winston",  userOutputDTO2.getFirstName());
        assertEquals("Kat",  userOutputDTO2.getLastName());
        assertEquals("winstonkat", userOutputDTO2.getPassword());
        assertEquals("winstonkat@gmail.com",  userOutputDTO2.getEmail());
        //hoe Rolename krijgen?

    }

    @Test
    void shouldGetUserById(){
        //arrange

        Role role = new Role();
        role.setId(1L);
        role.setRoleName("customer");

        User user = new User();
        user.setId(123L);
        user.setFirstName("Lizzy");
        user.setLastName("Telford");
        user.setPassword("lizzytelford");
        user.setEmail("lizzytelford@gmail.com");
        user.setRole(role);

        when(userRepository.findById(123L)).thenReturn(Optional.of(user));

        //act
        UserOutputDTO userOutputDTO = userService.getUserById(123L);

        //assert
        assertEquals(123L, userOutputDTO.getId());
        assertEquals("Lizzy", userOutputDTO.getFirstName());
        assertEquals("Telford", userOutputDTO.getLastName());
        assertEquals("lizzytelford", userOutputDTO.getPassword());
        assertEquals("lizzytelford@gmail.com", userOutputDTO.getEmail());
        assertEquals(1L, userOutputDTO.getRoleId());

    }

    @Test
    void shouldThrowRecordNotFoundExceptionWhenUserNotFound(){
        //arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //act and assert
        assertThrows(RecordNotFoundException.class, () -> userService.getUserById(userId));

    }

    @Test
    void shouldDeleteUserById(){
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

        assertThrows(RecordNotFoundException.class, () -> userService.deleteUser(userId));

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
        role.setRoleName("Admin");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstName("Lizzy");
        existingUser.setLastName("Telford");
        existingUser.setPassword("lizzytelford");
        existingUser.setEmail("lizzytelford@hotmail.com");
        existingUser.setRoleid(1L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        when(roleRepository.findById(2L)).thenReturn(Optional.of(role));

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User updatedUser = invocation.getArgument(0);
            // Simulate the save operation by returning the updated user
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



}

