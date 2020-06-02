package com.AthorizationAndAuthentication.AthorizationAndAuthentication.model;

import javax.persistence.*;

@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String token;

    @OneToOne(targetEntity = EndUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "enduser_id")
    private EndUser endUser;

    public VerificationToken(){

    }

    public VerificationToken(String token, EndUser user) {
        this.token = token;
        this.endUser = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public EndUser getEndUser() {
        return endUser;
    }

    public void setEndUser(EndUser user) {
        this.endUser = user;
    }
}
