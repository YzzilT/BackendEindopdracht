package com.example.backendeindopdracht;


import com.example.backendeindopdracht.controller.RoleController;
import com.example.backendeindopdracht.controller.UserController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BackendEindopdrachtApplicationTests {
	@Autowired
	RoleController roleController;
	@Autowired
	UserController userController;

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	static void setup(){
		//https://spring.io/guides/gs/testing-web/
		//https://www.baeldung.com/spring-boot-testing
	}
	@Test
	void createRoleAndUser() throws Exception {



		assertThat(roleController).isNotNull();


		var jsonrole = """
				{
					 "roleName" : "Customer"
				 }
				""";
		this.mockMvc.perform(post("/roles").content(jsonrole).contentType(MediaType.APPLICATION_JSON).header("Authorization", "test"));


		var user = """
		{
			"firstName" : "Lizzy",
			"lastName" : "Telford",
			"password" : "lizzytelford",
			"email" : "lizzytelford@hotmail.com",
			"roleid" : 1
		}
		""";

		this.mockMvc.perform(post("/users").content(user).contentType(MediaType.APPLICATION_JSON).header("Authorization", "test"));

		var getUser = """
				{				{
				        "id": 1,
				        "firstName": "Lizzy",
				        "lastName": "Telford",
				        "password": "lizzytelford",
				        "email": "lizzytelford@hotmail.com",
				        "role": {
				            "id": 1,
				            "roleName": "Customer"
				        },
				        "roleid": 1,
				        "jwt": "91b05f19-e1fe-4514-9012-18294eddba69",
				        "orders": []
				    }
				}
				""";
		this.mockMvc.perform(get("/users").header("Authorization", "test")).andExpect(jsonPath("$[0].firstName", is("Lizzy")));


	}

	@Test
	void loginUserAndRefuseUnauthorizedRequest() throws Exception {


		var jsonrole = """
				{
					 "roleName" : "FrontDesk"
				 }
				""";
		this.mockMvc.perform(post("/roles").content(jsonrole).contentType(MediaType.APPLICATION_JSON).header("Authorization", "test"));


		var user = """
		{
			"firstName" : "Lizzy",
			"lastName" : "Telford",
			"password" : "lizzytelford",
			"email" : "lizzytelford@hotmail.com",
			"roleid" : 1
		}
		""";
		this.mockMvc.perform(post("/users").content(user).contentType(MediaType.APPLICATION_JSON).header("Authorization", "test"));

		var logindata = """
			{
				 "email":"lizzytelford@hotmail.com",
				 "password":"lizzytelford"
			 }
		""";
		var x = this.mockMvc.perform(post("/login").content(logindata).contentType(MediaType.APPLICATION_JSON));
		var jwt = x.andReturn().getResponse().getContentAsString();

		var repairdata = """
			{
				  "problemDescription":"auto doet het niet",
				  "userid":1,
				  "productid":1
			}
		""";
		this.mockMvc.perform(post("/repair").content(repairdata).contentType(MediaType.APPLICATION_JSON).header("Authorization", jwt))
				.andExpect(status().isUnauthorized());




	}

}
