package com.Message.Message.security;

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

import com.Message.Message.security.*;
import com.Message.Message.security.jwt.*;
import com.Message.Message.model.*;
import com.Message.Message.service.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

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
                .ignoringAntMatchers("/h2-console/**").and()

                // ZBOG H2 BAZE CSRF INGORING I HEAERS.FRAME
                // .csrf().ignoringAntMatchers("/h2-console/**")
                // .and()
                // Stavlja se zbog h2 baze
                // .headers().frameOptions().sameOrigin()
                // .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers("/hello", "/getAllAdvertisementsForCart", "/logOut", "/callMe",
                        "/getAllAdvertisementsForCart", "/h2-console/**")
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