package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.repository.UserRepository;
import com.example.backendeindopdracht.service.UserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping()
public class LoginController {

    private final UserRepository userRepository;

    @PostMapping("login")
    public ResponseEntity<String> login (HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        String requestBody = convertInputStreamToString(request.getInputStream());

        JsonObject jsonObject = new JsonParser().parse(requestBody).getAsJsonObject();
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();


        User user2 = null;
        for(var user : userRepository.findAll()){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                user2 = user;
                break;
            }
        }
        if(user2 == null){
            return ResponseEntity.status(404).body("not found");
        }
        //todo privatekey aanmaken
        //todo jwt veld toevoegen op user

//        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
//        keyGenerator.initialize(1024);
//
//        KeyPair kp = keyGenerator.genKeyPair();
//        PublicKey publicKey = (PublicKey) kp.getPublic();
//        PrivateKey privateKey = (PrivateKey) kp.getPrivate();
//        var jwttoken = generateJwtToken(privateKey,user2.getEmail(),user2.getRole().getRoleName());

        var jwttoken = UUID.randomUUID().toString();
        user2.setJwt(jwttoken);
        userRepository.save(user2);



        //username password komt binnen
        //bijbehorende user vinden
        //jwt genereren en opslaan op de user
        //jwt returnen

        return ResponseEntity.ok(jwttoken);
    }


    private String convertInputStreamToString(ServletInputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, bytesRead));
        }
        return sb.toString();
    }

//    https://wstutorial.com/misc/jwt-java-public-key-rsa.html
    public String generateJwtToken(PrivateKey privateKey,String username,String role) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(Date.from(Instant.now().plus(8, ChronoUnit.HOURS)))
                .setIssuer("lizzy")
                .claim("groups", new String[] { role })
                .signWith(SignatureAlgorithm.RS256, privateKey).compact();
        return token;
    }

}
