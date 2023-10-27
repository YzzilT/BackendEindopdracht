package com.example.backendeindopdracht.service;
import static org.mockito.Mockito.*;

import com.example.backendeindopdracht.DTO.inputDTO.RoleInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RoleOutputDTO;
import com.example.backendeindopdracht.controller.RoleController;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

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
        doReturn(Optional.of(role)).when(roleRepository).findById(1L);

        RoleOutputDTO roleOutputDTO = roleService.getRoleById(1L);

        //assert
        assertEquals(1L, roleOutputDTO.getId());
        assertEquals("Testrol", roleOutputDTO.getRoleName());
    }

    @Test
    void shouldThrowRecordNotFoundExceptionWhenRoleNotFoundInGet(){

        when(roleRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(RecordNotFoundException.class, () -> roleService.getRoleById(1L));

    }



    @Test
    void shouldUpdateRole(){

        RoleInputDTO roleInputDTO = new RoleInputDTO();
        roleInputDTO.setRoleName("new role name");

        Role existingRole = new Role();
        existingRole.setId(1L);
        existingRole.setRoleName("old role name");

        Role updatedRole = new Role();
        updatedRole.setId(1L);
        updatedRole.setRoleName("new role name");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(existingRole));
        when(roleRepository.save(any(Role.class))).thenReturn(updatedRole);

        RoleOutputDTO roleOutputDTO = roleService.updateRole(roleInputDTO, 1L);

        assertEquals(1L, roleOutputDTO.getId());
        assertEquals("new role name", roleOutputDTO.getRoleName());
    }

    @Test
    void shouldThrowRecordNotFoundExceptionWhenRoleNotFoundInUpdate() {

        RoleInputDTO roleInputDTO = new RoleInputDTO();
        roleInputDTO.setRoleName("newRoleName");

        when(roleRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(RecordNotFoundException.class, () -> roleService.updateRole(roleInputDTO, 1L));
    }




    @Test
    void shouldDeleteExistingRole() {

        Long roleId = 2L;
        Role role = new Role();
        role.setId(roleId);
        role.setRoleName("testrol");
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        Role deletedRole = roleService.deleteRole(roleId);

        assertEquals(roleId, deletedRole.getId());
        assertEquals("testrol", deletedRole.getRoleName());


    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentRole(){

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
