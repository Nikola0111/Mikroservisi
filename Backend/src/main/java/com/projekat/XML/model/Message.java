package com.projekat.XML.model;

import java.time.LocalDateTime;
import com.projekat.XML.model.EntityUser;
import javax.persistence.*;

import com.projekat.XML.dtos.MessageDTO;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn
    private EntityUser sender;

    @ManyToOne
    @JoinColumn
    private EntityUser receiver;

    private String text;

    private LocalDateTime timeSent;


    public EntityUser getSender() {
        return this.sender;
    }

    public void setSender(EntityUser sender) {
        this.sender = sender;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityUser getReceiver() {
        return this.receiver;
    }

    public void setReceiver(EntityUser receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimeSent() {
        return this.timeSent;
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }

    public Message() {

    }

    public Message(EntityUser sender, EntityUser receiver, String text, LocalDateTime timeSent) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.timeSent = timeSent;
    }

}