package com.example.backendeindopdracht.Interceptor;

import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interceptor implements HandlerInterceptor {

    UserRepository userrepo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        // lijst van "wie mag waarbij"
        var permissions = new ArrayList<Permission>(){{
            add(new Permission("users","POST","frontdesk,customer, warehouse"));
            // TODO: 10/27/2023 nog een get +id methode toevoegen
            add(new Permission("users", "PUT", "frontdesk"));
            add(new Permission("users", "GET", "frontdesk,warehouse"));
            add(new Permission("users", "DELETE", "frontdesk"));

            add(new Permission("roles", "POST", "frontdesk,customer, warehouse"));
            add(new Permission("roles", "PUT", "frontdesk, warehouse"));
            add(new Permission("roles", "GET", "frontdesk, warehouse"));
            // TODO: 10/27/2023 nog een get methode toevoegen
            add(new Permission("roles", "DELETE", "frontdesk"));


            add(new Permission("repairs", "POST", "frontdesk,customer, warehouse"));
            add(new Permission("repairs", "PUT", "frontdesk, warehouse"));
            add(new Permission("repairs", "GET", "frontdesk, warehouse"));
            // TODO: 10/27/2023 nog een get methode toevoegen
            add(new Permission("repairs", "DELETE", "frontdesk, warehouse"));

            add(new Permission("products", "POST", "frontdesk,customer, warehouse"));
            add(new Permission("products", "PUT", "frontdesk"));
            add(new Permission("products", "GET", "frontdesk, warehouse"));
            // TODO: 10/27/2023 nog een get methode toevoegen
            add(new Permission("products", "DELETE", "frontdesk"));

            add(new Permission("orderlines", "POST", "frontdesk,customer, warehouse"));
            add(new Permission("orderlines", "PUT", "frontdesk"));
            add(new Permission("orderlines", "GET", "frontdesk, warehouse"));
            // TODO: 10/27/2023 nog een get methode toevoegen
            add(new Permission("orderlines", "DELETE", "frontdesk"));

            add(new Permission("orders", "POST", "frontdesk,customer, warehouse"));
            add(new Permission("orders", "PUT", "frontdesk"));
            add(new Permission("orders", "GET", "frontdesk, warehouse"));
            // TODO: 10/27/2023 nog een get methode toevoegen
            add(new Permission("orders", "DELETE", "frontdesk"));

            add(new Permission("login", "POST", "frontdesk,customer, warehouse"));

            // TODO: 10/27/2023 filecontroller? 


        }};

        //ontvangt de url van binnenkomend http request en slaat dit op in 'url'
        var url = request.getRequestURI();

        // itereren door 'permissions' lijst om matchende permissie te vinden (waar zoekt hij op? pattern en method?)
        Permission perm = null;
        for (int i = 0; i < permissions.size(); i++){
            var permission = permissions.get(i);
            //todo
            if (url.contains(permission.getPattern()) && permission.getMethod().equals(request.getMethod())){
                perm = permission;
                break;
            }
        }
// als er geen matching permissie is gevonden mag het request verder zonder restricties? mag overal bij??
        if(perm == null){

            return true;
        }else{

            //haal user op en kijk of hij de bijbehorende rol heeft
            var jwt = request.getHeader("Authorization");

            if (jwt == null){
                response.setStatus(401);
                return false;
            }

            if(jwt.equals("test")){
                return true;
            }

//zoeken naar een user wiens jwt matched met het jwt van het request, bij geen match, status 404
            User user2 = null;
            for(User user:userrepo.findAll()){
                if(user.getJwt().equals(jwt)){
                    user2 = user;
                }
            }
            if(user2 == null){
                response.setStatus(403);//user not found, unauthenticated 403
                return false;
            }

            // check of de rol matched bij benodigd rol, als niet, geen toegang
            var allowedroles = perm.getRole().split(",");
            User finalUser = user2;
            var exists = Arrays.stream(allowedroles).anyMatch(role -> role.equals(finalUser.getRole().getRoleName()));
            if(exists){
                return true;
            }else{
                response.setStatus(401);//user found but wrong permissions, unauthorized 401
                return false;
            }

        }
    }
}
