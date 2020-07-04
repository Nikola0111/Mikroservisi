package com.projekat.XML.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

//implements UserDetails

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loginInfo",
        namespace="com.projekat.XML.model",
        propOrder = {"id", "username", "password", "isAccountNonExpired", "isAccountNonLocked", "isCredentialsNonExpired", "isEnabled",
        "email", "salt"})
@Entity
public class LoginInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name="id", required = true)
    private Long id;

    @XmlElement(name="username", required = true)
     private String username;

    @XmlElement(name="password", required = true)
    private  String password;

    @XmlElement(name="isAccountNonExpired", required = true)
    private  boolean isAccountNonExpired;

    @XmlElement(name="isAccountNonLocked", required = true)
    private  boolean isAccountNonLocked;

    @XmlElement(name="isCredentialsNonExpired", required = true)
    private  boolean isCredentialsNonExpired;

    @XmlElement(name="isEnabled", required = true)
    private  boolean isEnabled;

   // @NotBlank(message="Email must not be empty")
   // @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+.com")
    @XmlElement(name="email", required = true)
    private  String email;

    @XmlElement(name="salt", required = true)
    private  String salt;

 
   
public LoginInfo(){

}

    public LoginInfo(String username,
                           String password,
                           String email,
                       //    String salt,
                         //  Set<? extends GrantedAuthority> grantedAuthorities,
                           boolean isAccountNonExpired,
                           boolean isAccountNonLocked,
                           boolean isCredentialsNonExpired,
                           boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.email = email;
      //  this.salt=salt;
       // this.grantedAuthorities = grantedAuthorities;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public LoginInfo(String username, String email, String password){


        
    }

 
    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getSalt() {
        return salt;
    }

    
    public Long getId(){
        return id;
    }
    /*



    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    */


}        
