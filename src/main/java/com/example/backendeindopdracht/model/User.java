package com.example.backendeindopdracht.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


    public class User {

        private Long id;
        private String firstName;
        private String lastName;
        private String password;
        private String email;
        @Enumerated(EnumType.STRING)
        private Role role;
    }

