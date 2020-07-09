package com.AthorizationAndAuthentication.AthorizationAndAuthentication.model;

import java.util.Date;

import javax.persistence.*;

@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String token;

    @OneToOne(targetEntity = EndUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private EndUser user;

    private Date expiryDate;

    public VerificationToken(){

    }

    public VerificationToken(String token, EndUser user) {
        this.token = token;
        this.user = user;
        this.expiryDate = new Date();
    }

    public Date getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public EndUser getUser() {
        return user;
    }

    public void setUser(EndUser user) {
        this.user = user;
    }
}
