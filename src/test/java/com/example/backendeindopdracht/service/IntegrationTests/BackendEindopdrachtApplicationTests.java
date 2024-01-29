package com.example.backendeindopdracht.service.IntegrationTests;


import com.example.backendeindopdracht.controller.RoleController;
import com.example.backendeindopdracht.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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

	@Test
	void loginUserAndRefuseUnauthorizedRequest(){

		try {

			var logindata = """
						{
							 "username" : "Lizzy",
							 "password":"lizzytelford"
						 }
					""";
			var x = this.mockMvc.perform(post("/auth").content(logindata).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andReturn();
			var jwt = x.getResponse().getHeader("Authorization").substring("Bearer ".length());
			var repairdata = """
						{
							  "problemDescription":"auto doet het niet",
							  "userid":1,
							  "productid":1
						}
					""";
			this.mockMvc.perform(post("/repairs").content(repairdata).contentType(MediaType.APPLICATION_JSON).header("Authorization", jwt))
					.andExpect(status().isForbidden());

		}catch (Exception e){
			System.out.println(e);

		}
	}
}


