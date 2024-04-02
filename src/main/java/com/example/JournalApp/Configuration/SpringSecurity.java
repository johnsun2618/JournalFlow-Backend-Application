package com.example.JournalApp.Configuration;

import com.example.JournalApp.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SpringSecurity {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    @Bean
//    //authentication
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder){
//        UserDetails admin = User.withUsername("Anmol")
//                .password(encoder.encode("pwd1"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withUsername("Abinash")
//                .password(encoder.encode("pwd2"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/journal/**", "/user/**").authenticated()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().permitAll());

        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
