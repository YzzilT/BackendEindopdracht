package com.example.backendeindopdracht.DTO.inputDTO;

import com.example.backendeindopdracht.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
