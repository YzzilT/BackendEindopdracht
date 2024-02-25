package com.example.backendeindopdracht.service.UnitTests;
import static org.mockito.Mockito.*;

import com.example.backendeindopdracht.DTO.inputDTO.RoleInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RoleOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.RoleRepository;
import com.example.backendeindopdracht.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

@MockBean
@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
   @InjectMocks
   private RoleService roleService;

    @Test
    void ShouldAddRoleName() {


        RoleInputDTO roleInputDTO = new RoleInputDTO();
        roleInputDTO.setRoleName("Admin");

        Role role = new Role();
        role.setRoleName("Admin");

        Mockito.lenient().when(roleRepository.save(any(Role.class))).thenReturn(role);


        RoleOutputDTO result = roleService.addRole(roleInputDTO);

        assertNotNull(result);
        assertEquals("Admin", result.getRoleName());
    }

    @Test
    void ShouldAddRoleId() {


        RoleInputDTO roleInputDTO = new RoleInputDTO();
        roleInputDTO.setId(987L);

        Role role = new Role();
        role.setId(987L);


        Mockito.lenient().when(roleRepository.save(any(Role.class))).thenReturn(role);


        RoleOutputDTO result = roleService.addRole(roleInputDTO);



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

        verify(roleRepository, times(1)).findAll();

        //assert
        assertEquals(2, roleOutputDTOList.size());

        assertEquals(1L, roleOutputDTOList.get(0).getId());
        assertEquals("Role1", roleOutputDTOList.get(0).getRoleName());

        assertEquals(2L, roleOutputDTOList.get(1).getId());
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
    void shouldThrowResponseStatusExceptionWhenRoleNotFoundInGet() {
        lenient().when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> roleService.getRoleById(1L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("role not found", exception.getReason());
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

        lenient().when(roleRepository.findById(1L)).thenReturn(Optional.of(existingRole));
        lenient().when(roleRepository.save(any(Role.class))).thenReturn(updatedRole);

        RoleOutputDTO roleOutputDTO = roleService.updateRole(roleInputDTO, 1L);

        assertEquals(1L, roleOutputDTO.getId());
        assertEquals("new role name", roleOutputDTO.getRoleName());
    }

    @Test
    void shouldThrowRecordNotFoundExceptionWhenRoleNotFoundInUpdate() {

        RoleInputDTO roleInputDTO = new RoleInputDTO();
        roleInputDTO.setRoleName("newRoleName");



        assertThrows(RecordNotFoundException.class, () -> roleService.updateRole(roleInputDTO, 1L));
    }




    @Test
    void shouldDeleteExistingRole() {
        // Arrange
        Long roleId = 1L;
        Role role = new Role();
        role.setId(roleId);
        role.setRoleName("testRole");

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        // Act
        Role deletedRole = roleService.deleteRole(roleId);

        // Assert
        assertNotNull(deletedRole);
        assertEquals(roleId, deletedRole.getId());
        assertEquals("testRole", deletedRole.getRoleName());

        // Verify that the repository delete method is called with the correct role
        verify(roleRepository, times(1)).delete(role);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentRole(){

        Long roleId = 2L;

        lenient().when(roleRepository.findById(roleId)).thenReturn(Optional.empty());


        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> roleService.deleteRole(roleId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("role not found", exception.getReason());

    }

    @Test
    void shouldTransferInputDtoToRole(){
        RoleInputDTO roleInputDTO = new RoleInputDTO();
        roleInputDTO.setId(1L);
        roleInputDTO.setRoleName("Test role");

        Role role = new Role();
        role.setId(1L);
        role.setRoleName("Test role");

        lenient().when(roleRepository.findById(1L)).thenReturn(Optional.of(role));


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
