package com.AthorizationAndAuthentication.AthorizationAndAuthentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import javax.crypto.SecretKey;

import com.AthorizationAndAuthentication.AthorizationAndAuthentication.security.*;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.security.jwt.*;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.*;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.service.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final LoginInfoService loginInfoService;

    private final KeyPairClassService keyPairClassService;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, LoginInfoService loginInfoService,
            KeyPairClassService keyPairClassService) {
        this.passwordEncoder = passwordEncoder;
        this.loginInfoService = loginInfoService;
        this.keyPairClassService = keyPairClassService;
        this.keyPairClassService.setKeyPair();

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                // .csrf().disable()
                // odkomentarisati radi bezbednosti
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/callMe", "/increaseEndUsersNumberOfAds", "/getCsrf","/dodajUsere", "/getUserByUsername/**","/registrationConfirm",
                        "/h2-console/**")
                .and()

                // ZBOG H2 BAZE CSRF INGORING I HEAERS.FRAME
                // .csrf().ignoringAntMatchers("/h2-console/**")
                // .and()
                // Stavlja se zbog h2 baze
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(keyPairClassService.getPublicKey()),
                        JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login", "/getCsrf", "/getAll", "/getUserByUsername/**", "/getUserId","/registrationConfirm",
                        "/getLoggedEndUser", "/increaseEndUsersNumberOfAds", "/getAgentEmail", "/getAgentIDByUserID",
                        "/getAgentIDByMail",  "/getEmail", "getPublicKey", "/register", "/loginToken","/dodajUsere",
                        "/logout", "/h2-console/**")
                .permitAll().anyRequest().authenticated();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProviderOurs daoAuthenticationProvider() {
        DaoAuthenticationProviderOurs provider = new DaoAuthenticationProviderOurs();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        provider.setUserDetailsService(loginInfoService);
        return provider;
    }

}