package com.example.eTransact.config;

import com.example.eTransact.Model.User;
import com.example.eTransact.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity httpSecurity){
        return httpSecurity.authorizeHttpRequests(request->
                request.requestMatchers(HttpMethod.POST, "/user/addacount").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/user/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/user").hasAnyRole("USER")
                        .requestMatchers("/account/**").hasAnyRole("USER")
                        .anyRequest().permitAll()

                ).csrf(httpSecurityCsrfConfigurer ->
                httpSecurityCsrfConfigurer.ignoringRequestMatchers("/user/**", "/account/**"))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return username -> {
            Optional<User> user = userRepository.findByEmail(username);
            if (user.isPresent())
                return user.get();
            throw new UsernameNotFoundException("username: " + username + "doesn't exist");
        };
    }
}
