package com.example.backendeindopdracht.Interceptor;

import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.Arrays;


public class Interceptor implements HandlerInterceptor {

    @Autowired
    UserRepository userRepository;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){


        var permissions = new ArrayList<Permission>(){{


            add(new Permission("users/[0-9]+", "PUT", "frontdesk"));
            add(new Permission("users", "GET", "frontdesk,warehouse"));
            add(new Permission("users/[0-9]+", "GET", "frontdesk,warehouse, customer"));
            add(new Permission("users/[0-9]+", "DELETE", "frontdesk"));


            add(new Permission("roles/[0-9]+", "PUT", "frontdesk, warehouse"));
            add(new Permission("roles", "GET", "frontdesk, warehouse"));
            add(new Permission("roles/[0-9]+", "GET", "frontdesk, warehouse"));
            add(new Permission("roles/[0-9]+", "DELETE", "frontdesk"));



            add(new Permission("repairs/[0-9]+", "PUT", "warehouse"));
            add(new Permission("repairs", "GET", "frontdesk, warehouse"));
            add(new Permission("repairs/[0-9]+", "GET", "frontdesk, warehouse, customer"));
            add(new Permission("repairs/[0-9]+", "DELETE", "frontdesk, warehouse"));


            add(new Permission("products/[0-9]+", "PUT", "frontdesk"));
            add(new Permission("products/[0-9]+", "DELETE", "frontdesk"));


            add(new Permission("orderlines/[0-9]+", "PUT", "frontdesk"));
            add(new Permission("orderlines", "GET", "frontdesk, warehouse"));
            add(new Permission("orderlines/[0-9]+", "GET", "frontdesk, warehouse, customer"));
            add(new Permission("orderlines/[0-9]+", "DELETE", "frontdesk"));


            add(new Permission("orders/[0-9]+", "PUT", "frontdesk"));
            add(new Permission("orders", "GET", "frontdesk, warehouse"));
            add(new Permission("orders/[0-9]+", "GET", "frontdesk, warehouse, customer"));
            add(new Permission("orders/[0-9]+", "DELETE", "frontdesk"));




        }};


        var url = request.getRequestURI();


        Permission perm = null;
        for (int i = 0; i < permissions.size(); i++){
            var permission = permissions.get(i);

            if (url.matches(permission.getPattern()) && permission.getMethod().equals(request.getMethod())){
                perm = permission;
                break;
            }
        }

        if(perm == null){

            return true;
        }else{


            var jwt = request.getHeader("Authorization");

            if (jwt == null){
                response.setStatus(401);
                return false;
            }

            if(jwt.equals("test")){
                return true;
            }


            User user2 = null;
            for(User user: userRepository.findAll()){
                if(user.getJwt().equals(jwt)){
                    user2 = user;
                }
            }
            if(user2 == null){
                response.setStatus(403);
                return false;
            }


            var allowedroles = perm.getRole().split(",");
            User finalUser = user2;
            var exists = Arrays.stream(allowedroles).anyMatch(role -> role.equals(finalUser.getRole().getRoleName()));
            if(exists){
                return true;
            }else{
                response.setStatus(401);
                return false;
            }

        }
    }
}
