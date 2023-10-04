package com.example.backendeindopdracht.DTO.inputDTO;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInputDTO {


    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Long roleid;

}
