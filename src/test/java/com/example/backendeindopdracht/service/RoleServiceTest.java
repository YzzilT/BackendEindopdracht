package com.example.backendeindopdracht.service;
import static org.mockito.Mockito.*;

import com.example.backendeindopdracht.DTO.inputDTO.RoleInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RoleOutputDTO;
import com.example.backendeindopdracht.controller.RoleController;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
   @InjectMocks
   private RoleService roleService;

   @InjectMocks
   private RoleController roleController;

    @Captor
    private ArgumentCaptor<Role> roleCaptor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void ShouldAddRoleName() {

        //arrange
        RoleInputDTO roleInputDTO = new RoleInputDTO();
        roleInputDTO.setRoleName("Admin");

        Role role = new Role();
        role.setRoleName("Admin");

        when(roleRepository.save(any(Role.class))).thenReturn(role);



        //act
        RoleOutputDTO result = roleService.addRole(roleInputDTO);



        //assert
        assertNotNull(result);
        assertEquals("Admin", result.getRoleName());
    }

    @Test
    void ShouldAddRoleId() {

        //arrange
        RoleInputDTO roleInputDTO = new RoleInputDTO();
        roleInputDTO.setId(987L);

        Role role = new Role();
        role.setId(987L);

        when(roleRepository.save(any(Role.class))).thenReturn(role);



        //act
        RoleOutputDTO result = roleService.addRole(roleInputDTO);



        //assert
        assertNotNull(result);
        assertEquals(987L, result.getId());
    }

    @Test
    void shouldGetAllRoles() {

        //arrange
        Role role1 = new Role();
        role1.setId(1L);
        role1.setRoleName("Role1");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setRoleName("Role2");

        //act
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1,role2));

        List<RoleOutputDTO> roleOutputDTOList = roleService.getAllRoles();

        //assert
        assertEquals(2, roleOutputDTOList.size());

        assertEquals(1L, roleOutputDTOList.get(0).getId());
        assertEquals("Role1", roleOutputDTOList.get(0).getRoleName());

        assertEquals(2, roleOutputDTOList.get(1).getId());
        assertEquals("Role2", roleOutputDTOList.get(1).getRoleName());
        
    }

    @Test
    void getRoleById() {

        //arrange
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("Testrol");

        //act
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        RoleOutputDTO roleOutputDTO = roleService.getRoleById(1L);

        //assert
        assertEquals(1L, role.getId());
        assertEquals("Testrol", roleOutputDTO.getRoleName());
    }

    @Test
    void shouldReturnIdNotFound() {

        //arrange
        when(roleRepository.findById(2L)).thenReturn(Optional.empty());

        //act
        assertThrows(RecordNotFoundException.class, () -> roleService.getRoleById(2L));

        //assert
        verify(roleRepository, times(1)).findById(2L);
    }

    @Test
    void shouldUpdateRole(){

        Role existingRole = new Role();
        existingRole.setId(1L);

        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(existingRole));

        RoleInputDTO roleInputDTO = new RoleInputDTO();
        roleInputDTO.setRoleName("Updated Role");

        ResponseEntity<?> responseEntity = roleService.updateRole(roleInputDTO, 1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

//    Long roleId = 1L;
//    RoleInputDTO roleInputDTO = new RoleInputDTO();
//    Role exisingRole = new Role();
//    exisingRole.setId(roleId);
//
//    when(roleRepository.findById(roleId)).thenReturn(Optional.of(exisingRole));
//
//    ResponseEntity<?> responseEntity = roleController.updateRole(roleId, roleInputDTO);
//
//    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());


    @Test
    void shouldThrowRecordNotFoundExceptionWhenRoleNotFound(){
        RoleInputDTO roleInputDTO = new RoleInputDTO();
        roleInputDTO.setRoleName("Updated Role");

        ResponseEntity<?> responseEntity = roleService.updateRole(roleInputDTO, 2L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Role with id: 2 not found.", responseEntity.getBody());
    }
//        Long roleId = 1L;
//
//        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());
//
//        assertThrows(RecordNotFoundException.class, () -> roleService.updateRole(new RoleInputDTO(), roleId));




    @Test
    void shouldDeleteExistingRole() {

       Role existingRole = new Role();
       existingRole.setId(1L);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(existingRole));

        Role deletedRole = roleService.deleteRole(1L);

        verify(roleRepository).deleteById(1L);

        assertEquals(existingRole, deletedRole);

    }

    @Test
    void testDeleteNonExistentRole(){

        //arrange
        Long nonExistingRoleId = 2L;

        when(roleRepository.findById(nonExistingRoleId)).thenReturn(Optional.empty());

        //act and assert
        assertThrows(RecordNotFoundException.class, () -> {
        roleService.deleteRole(nonExistingRoleId);});

    }

    @Test
    void shouldTransferInputDtoToRole(){
        RoleInputDTO roleInputDTO = new RoleInputDTO();
        roleInputDTO.setId(1L);
        roleInputDTO.setRoleName("Test role");

        Role role = new Role();
        role.setId(1L);
        role.setRoleName("Test role");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));


        Role resultRole = roleService.transferInputDtoRoleToRole(roleInputDTO);

        assertEquals(1L, resultRole.getId());
        assertEquals("Test role", resultRole.getRoleName());
    }

    @Test
    void shouldTransferRoleToOutputDto(){
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("Test role");

        RoleOutputDTO roleOutputDTO = roleService.transferRoleToDTO(role);

        assertEquals(1L, roleOutputDTO.getId());
        assertEquals("Test role", roleOutputDTO.getRoleName());

    }
}
