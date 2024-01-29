package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.backendeindopdracht.DTO.inputDTO.*;
@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;


    @PostMapping("/auth")
    public ResponseEntity<Object> signIn(@RequestBody AuthDTO authDto) {
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(authDto.username, authDto.password);

        try {
            Authentication auth = authManager.authenticate(up);

            UserDetails ud = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(ud);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("Token generated");
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}


