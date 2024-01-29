package com.example.backendeindopdracht.Security;

import com.example.backendeindopdracht.repository.UserRepository;
import com.example.backendeindopdracht.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http

                .authorizeHttpRequests()


                //users
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("frontdesk", "warehouse")
                .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("frontdesk", "warehouse")
                .requestMatchers(HttpMethod.PUT, "/users/{id}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/users{id}").hasRole("frontdesk")

                //roles
                .requestMatchers(HttpMethod.POST, "/roles").permitAll()
                .requestMatchers(HttpMethod.GET, "/roles").hasAnyRole("frontdesk", "warehouse")
                .requestMatchers(HttpMethod.GET, "/roles/{id}").hasAnyRole("frontdesk", "warehouse")
                .requestMatchers(HttpMethod.PUT, "/roles/{id}").hasRole("frontdesk")
                .requestMatchers(HttpMethod.DELETE, "/roles{id}").hasRole("frontdesk")

                //repairs
                .requestMatchers(HttpMethod.POST, "/repairs").hasAnyRole("warehouse")
                .requestMatchers(HttpMethod.GET, "/repairs").hasAnyRole("frontdesk", "warehouse")
                .requestMatchers(HttpMethod.GET, "/repairs/{id}").hasAnyRole("frontdesk", "warehouse")
                .requestMatchers(HttpMethod.PUT, "/repairs/{id}").hasRole("frontdesk")
                .requestMatchers(HttpMethod.DELETE, "/repairs/{id}").hasAnyRole("frontdesk", "warehouse")

                //products
                .requestMatchers(HttpMethod.POST, "/products").permitAll()
                .requestMatchers(HttpMethod.GET, "products/restock").hasRole("frontdesk")
                .requestMatchers(HttpMethod.GET, "/products").hasRole("frontdesk")
                .requestMatchers(HttpMethod.GET, "/products/{id}").hasRole("frontdesk")
                .requestMatchers(HttpMethod.PUT, "/products/{id}").hasRole("frontdesk")
                .requestMatchers(HttpMethod.DELETE, "/products/{id}").hasRole("frontdesk")

                //orderlines
                .requestMatchers(HttpMethod.POST, "/orderlines").permitAll()
                .requestMatchers(HttpMethod.GET, "/orderlines").hasAnyRole("frontdesk", "warehouse")
                .requestMatchers(HttpMethod.GET, "/orderlines/{id}").hasAnyRole("frontdesk", "warehouse")
                .requestMatchers(HttpMethod.PUT, "/orderlines/{id}").hasAnyRole("frontdesk", "customer")
                .requestMatchers(HttpMethod.DELETE, "/orderlines/{id}").hasAnyRole("frontdesk", "customer")

                //orders
                .requestMatchers(HttpMethod.POST, "/orders").hasAnyRole("frontdesk", "customer")
                .requestMatchers(HttpMethod.GET, "/orders").hasAnyRole("frontdesk", "warehouse")
                .requestMatchers(HttpMethod.GET, "/orders/{id}").hasAnyRole("frontdesk", "warehouse")
                .requestMatchers(HttpMethod.PUT, "/orders/{id}").hasAnyRole("frontdesk", "warehouse")
                .requestMatchers(HttpMethod.DELETE, "/orders/{id}").hasAnyRole("frontdesk")

                //invoices
                .requestMatchers(HttpMethod.POST, "/invoices").hasAnyRole("frontdesk", "customer")
                .requestMatchers(HttpMethod.GET, "/invoices").hasRole("frontdesk")
                .requestMatchers(HttpMethod.GET, "/invoices/{id}").hasRole("frontdesk")
                .requestMatchers(HttpMethod.GET, "/invoices/totalAmount/{orderid}").hasAnyRole("frontdesk", "customer")
                .requestMatchers(HttpMethod.PUT, "/invoices/{id}").hasAnyRole("frontdesk", "warehouse")
                .requestMatchers(HttpMethod.DELETE, "/invoices/{id}").hasAnyRole("frontdesk")

                //image
                .requestMatchers(HttpMethod.POST, "/image").permitAll()
                .requestMatchers(HttpMethod.GET, "/image/{fileName}").permitAll()

                //auth
                .requestMatchers("/auth").permitAll()


                .anyRequest().denyAll()
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);



        return http.build();
    }



    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService udService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(udService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }

}