package com.example.backendeindopdracht.DTO.outputDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserOutputDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Long roleId;


}
