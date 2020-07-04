package com.projekat.XML.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String value;

    @Column
    private Date date;

    @ManyToOne
    private Advertisement ad;

    @ManyToOne
    private EndUser endUser;

    @Column
    private Double grade;

    @OneToOne
    private Reply reply;

    public Comment(){

    }

    public Comment(String value, Date date, Advertisement ad) {
        this.value = value;
        this.date = date;
        this.ad = ad;
    }

    public Comment(String value, Date date, Advertisement ad, EndUser endUser) {
        this.value = value;
        this.date = date;
        this.ad = ad;
        this.endUser = endUser;
    }

    public Comment(String value, Date date, Advertisement ad, EndUser endUser, Double grade) {
        this.value = value;
        this.date = date;
        this.ad = ad;
        this.endUser = endUser;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Advertisement getAd() {
        return ad;
    }

    public void setAd(Advertisement ad) {
        this.ad = ad;
    }

    public EndUser getEndUser() {
        return endUser;
    }

    public void setEndUser(EndUser endUser) {
        this.endUser = endUser;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", date=" + date +
                ", ad=" + ad +
                ", endUser=" + endUser +
                ", grade=" + grade +
                '}';
    }
}
