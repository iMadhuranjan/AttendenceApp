package com.attendence.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.attendence.app.service.SecurityUserDetailService;

@Configuration
public class AppSecurity {

    @Autowired
    private SecurityUserDetailService detailService;


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(detailService);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(aut->
        aut
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
        .anyRequest().permitAll()
        );

        http.formLogin(form->
        form.loginPage("/login")
        .loginProcessingUrl("/authenticate")
        // .successForwardUrl("/user/dashboard")
        );

        http.logout(log->
        log
        // .logoutUrl("/off")
        // .logoutSuccessUrl("/login?logout=true")
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login?logout=true")
        );

        return http.build();
    }
}
