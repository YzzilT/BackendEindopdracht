package com.example.backendeindopdracht.service;
import static org.mockito.Mockito.*;

import com.example.backendeindopdracht.DTO.inputDTO.RoleInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RoleOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
    @Mock
   private RoleService roleService;

    @BeforeEach
    public void setUp(){
        roleService = new RoleService(roleRepository);
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
}