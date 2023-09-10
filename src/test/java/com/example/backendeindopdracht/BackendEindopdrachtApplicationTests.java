package com.example.backendeindopdracht;

import com.example.backendeindopdracht.controller.RoleController;
import com.example.backendeindopdracht.controller.UserController;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.RoleRepository;
import com.example.backendeindopdracht.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendEindopdrachtApplicationTests {

	UserController userController;
	RoleController roleController;

	@BeforeAll
	void setupTestData(){
			roleController.addRole(new Role());
	}

	@Test
	void contextLoads() {
	}

}
