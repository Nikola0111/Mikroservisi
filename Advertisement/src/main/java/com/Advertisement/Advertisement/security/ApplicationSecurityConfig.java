package com.Advertisement.Advertisement.security;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    private PublicKeyClassService publicKeyClassService;

    private final LoginInfoService loginInfoService;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, LoginInfoService loginInfoService) {
        this.passwordEncoder = passwordEncoder;
        // this.publicKeyClassService = publicKeyClassService;
        this.loginInfoService = loginInfoService;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                // .csrf().disable()
                // odkomentarisati radi bezbednosti
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/save", "/saveImage", "/h2-console/**").and()

                // ZBOG H2 BAZE CSRF INGORING I HEAERS.FRAME
                // .csrf().ignoringAntMatchers("/h2-console/**")

                // Stavlja se zbog h2 baze
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/hello", "/save", "/getCsrf", "/saveImage", "/logOut", "/getAllAdvertisementsForCart",
                        "/callMe", "/getAllAdvertisementsForCart", "/h2-console/**")
                .permitAll().anyRequest().authenticated();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProviderOurs daoAuthenticationProvider() {
        DaoAuthenticationProviderOurs provider = new DaoAuthenticationProviderOurs();
        /// provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        provider.setUserDetailsService(loginInfoService);
        return provider;
    }

}