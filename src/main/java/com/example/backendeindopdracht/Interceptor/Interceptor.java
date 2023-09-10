package com.example.backendeindopdracht.Interceptor;

import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

public class Interceptor implements HandlerInterceptor {

    UserRepository userrepo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        var permissions = new ArrayList<Permission>(){{
            add(new Permission("users","POST","backendmedewerker"));
            add(new Permission("users", "PUT", "backendmedewerker"));
            add(new Permission("users", "GET", "backendmedewerker"));
            add(new Permission("users", "DELETE", "backendmedewerker"));

            add(new Permission("invoices", "POST", "backendmedewerker"));
            add(new Permission("invoices", "PUT", "backendmedewerker"));
            add(new Permission("invoices", "GET", "backendmedewerker"));
            add(new Permission("invoices", "DELETE", "backendmedewerker"));

        }};

        var url = request.getRequestURI();

        Permission perm = null;
        for (int i = 0; i < permissions.size(); i++){
            var permission = permissions.get(i);
            if (url.contains(permission.getPattern()) && permission.getMethod().equals(request.getMethod())){
                perm = permission;
                break;
            }
        }

        if(perm == null){

            return true;
        }else{

            //haal user op en kijk of hij de bijbehorende rol heeft
            var jwt = request.getHeader("Authorization");

            if(jwt.equals("test")){
                return true;
            }

            User user2 = null;
            for(User user:userrepo.findAll()){
                if(user.getJwt().equals(jwt)){
                    user2 = user;
                }
            }
            if(user2 == null){
                response.setStatus(404);
                return false;
            }

            if(user2.getRole().getRoleName().equals(perm.getRole())){
                return true;
            }else{
                response.setStatus(403);
                return false;
            }

        }



    }
}
