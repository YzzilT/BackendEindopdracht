package com.example.backendeindopdracht.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.repository.UserRepository;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping()
public class LoginController {

    private final UserRepository userRepository;

    @PostMapping("login")
    public ResponseEntity<String> login (HttpServletRequest request) throws IOException {
        String requestBody = convertInputStreamToString(request.getInputStream());

        JsonObject jsonObject = new JsonParser().parse(requestBody).getAsJsonObject();
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();


        User user2 = null;
        for(var user : userRepository.findAll()){
            //todo moet nog testen of dit goed gaat, anders nog even documentatie lezen op https://github.com/patrickfav/bcrypt
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if(user.getEmail().equals(email) && result.verified){
                user2 = user;
                break;
            }
        }
        if(user2 == null){
            return ResponseEntity.status(404).body("not found");
        }

        String jwttoken = generateJwtToken();
        user2.setJwt(jwttoken);
        userRepository.save(user2);
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
//    public String generateJwtToken(PrivateKey privateKey,String username,String role) {
//        String token = Jwts.builder()
//                .setSubject(username)
//                .setExpiration(Date.from(Instant.now().plus(8, ChronoUnit.HOURS)))
//                .setIssuer("lizzy")
//                .claim("groups", new String[] { role })
//                .signWith(SignatureAlgorithm.RS256, privateKey).compact();
//        return token;
//    }

    //    https://www.javainuse.com/spring/boot-jwt
    public String generateJwtToken() {

        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject("asd")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 10 * 1000))
                .setIssuer("lizzy")
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();
    }

}
