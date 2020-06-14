package com.Advertisement.Advertisement.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


import javax.crypto.SecretKey;

import com.Advertisement.Advertisement.security.*;
import com.Advertisement.Advertisement.security.jwt.*;
import com.Advertisement.Advertisement.model.*;
import com.Advertisement.Advertisement.service.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

 
    private final PasswordEncoder passwordEncoder;

    
    
    
@Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
      
      
     
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                
                
               
                //.csrf().disable()
                //odkomentarisati radi bezbednosti
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/callMe")
                .and()
                
                //ZBOG H2 BAZE CSRF INGORING I HEAERS.FRAME
                //.csrf().ignoringAntMatchers("/h2-console/**")
                //.and()
                //Stavlja se zbog h2 baze
                //.headers().frameOptions().sameOrigin()
                //.and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
               // .addFilterAfter(new CsrfFilter(csrfTokenRepository), CsrfFilter.class)
             
               // .addFilter(new JwtTokenVerifier(keyPairClassService.getPublicKey()),JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/hello","/callMe", "/register","/loginToken","/logout","/h2-console/**").permitAll()
                .anyRequest()
                .authenticated();
             
    }



}