package com.projekat.XML.model;

import com.projekat.XML.enums.UserType;
import com.projekat.XML.model.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entityUser",
        namespace="com.projekat.XML.model",
        propOrder = {"id", "name", "surname", "loginInfo", "jmbg", "phoneNumber", "userType"})
@Entity
@Table(name = "user_entity")
@Inheritance(strategy = InheritanceType.JOINED)
public class EntityUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name="id", required = true)
    private Long id;

 //   @Column(name = "name", nullable = false)
 //   @Size(min=2, max=40)
 //   @NotBlank(message="Name must not be empty")
 //   @Pattern(regexp="^$|[a-zA-Z ]+$", message="Name must not include special characters.")
     @XmlElement(name="name", required = true)
    private String name;

 //   @Column(name = "surname", nullable = false)
  //  @Size(min=2, max=40)
  //  @NotBlank(message="Surame must not be empty")
  //  @Pattern(regexp="^$|[a-zA-Z ]+$", message="Name must not include special characters.")
     @XmlElement(name="surname", required = true)
    private String surname;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_info", referencedColumnName = "id")
    @XmlElement(name="loginInfo", required = true)
    private LoginInfo loginInfo;

 //   @Column(name = "jmbg", unique = true, nullable = false)
  //  @Size(min=13, max=13)
 //   @NotBlank(message="JMBG must not be empty")
 //   @Pattern(regexp = "[0-9]{13}", message = "JMBG must only contain 13 digits.")
     @XmlElement(name="jmbg", required = true)
    private String jmbg;

  //  @Column(name = "phone_number")
  //  @Size(min=9, max=10)
  //  @NotBlank(message="Phone number must not be empty")
  //  @Pattern(regexp = "[0-9]{9,10}", message = "Phone number must only contain 9-10 digits.")
     @XmlElement(name="phoneNumber", required = true)
    private String phoneNumber;

    @Column(name = "user_type")
    @XmlElement(name="userType", required = true)
    private UserType userType;

    public EntityUser(String name, String surname, LoginInfo loginInfo, String jmbg, String phoneNumber, UserType ut) {
        this.name = name;
        this.surname = surname;
        this.loginInfo = loginInfo;
        this.jmbg = jmbg;
        this.phoneNumber = phoneNumber;
        this.userType = ut;
    }

    public EntityUser() {

    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPassword(){
        return this.loginInfo.getPassword();
    }


    public String getUsername(){
        return this.loginInfo.getUsername();
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", loginInfo=" + loginInfo +
                ", jmbg='" + jmbg + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userType=" + userType +
                '}';
    }
}
