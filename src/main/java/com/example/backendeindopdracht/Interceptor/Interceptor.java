//package com.example.backendeindopdracht.Interceptor;
//
//import com.example.backendeindopdracht.model.User;
//import com.example.backendeindopdracht.repository.UserRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Interceptor implements HandlerInterceptor {
//
//    UserRepository userrepo;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
//
//        // lijst van "wie mag waarbij"
//        var permissions = new ArrayList<Permission>(){{
//            add(new Permission("users","POST","backendmedewerker"));
//            add(new Permission("users", "PUT", "backendmedewerker"));
//            add(new Permission("users", "GET", "backendmedewerker"));
//            add(new Permission("users", "DELETE", "backendmedewerker"));
//
//            add(new Permission("invoices", "POST", "backendmedewerker"));
//            add(new Permission("invoices", "PUT", "backendmedewerker"));
//            add(new Permission("invoices", "GET", "backendmedewerker"));
//            add(new Permission("invoices", "DELETE", "backendmedewerker"));
//
//
//            add(new Permission("repair", "POST", "magazijnmedewerker"));
//            add(new Permission("repair", "PUT", "magazijnmedewerker"));
//            add(new Permission("repair", "GET", "magazijnmedewerker"));
//            add(new Permission("repair", "DELETE", "magazijnmedewerker"));
//
//
//        }};
//
//        //ontvangt de url van binnenkomend http request en slaat dit op in 'url'
//        var url = request.getRequestURI();
//
//        // itereren door 'permissions' lijst om matchende permissie te vinden (waar zoekt hij op? pattern en method?)
//        Permission perm = null;
//        for (int i = 0; i < permissions.size(); i++){
//            var permission = permissions.get(i);
//            if (url.contains(permission.getPattern()) && permission.getMethod().equals(request.getMethod())){
//                perm = permission;
//                break;
//            }
//        }
//// als er geen matching permissie is gevonden mag het request verder zonder restricties? mag overal bij??
//        if(perm == null){
//
//            return true;
//        }else{
//
//            //haal user op en kijk of hij de bijbehorende rol heeft
//            var jwt = request.getHeader("Authorization");
//
//            if (jwt == null){
//                response.setStatus(401);
//                return false;
//            }
//
//            if(jwt.equals("test")){
//                return true;
//            }
//
////zoeken naar een user wiens jwt matched met het jwt van het request, bij geen match, status 404
//            User user2 = null;
//            for(User user:userrepo.findAll()){
//                if(user.getJwt().equals(jwt)){
//                    user2 = user;
//                }
//            }
//            if(user2 == null){
//                response.setStatus(403);//user not found, unauthenticated 403
//                return false;
//            }
//
//            // check of de rol matched bij benodigd rol, als niet, geen toegang
//
//            if(user2.getRole().getRoleName().equals(perm.getRole())){
//                return true;
//            }else{
//                response.setStatus(401);//user found but wrong permissions, unauthorized 401
//                return false;
//            }
//
//        }
//
//
//
//    }
//}
