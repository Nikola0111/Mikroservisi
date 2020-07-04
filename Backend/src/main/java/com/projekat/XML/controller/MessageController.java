package com.projekat.XML.controller;

import java.util.List;

import com.projekat.XML.dtos.MessageDTO;
import com.projekat.XML.model.EntityUser;
import com.projekat.XML.model.Message;
import com.projekat.XML.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "inbox")
public class MessageController {

@Autowired
MessageService messageService;



//TRENUTNO DOBAVLJA SVE, TREBA messageService.GetInboxUsers()
    @GetMapping(value = "/allInboxUsers")
    public ResponseEntity<List<EntityUser>> getInboxUsers() {
		List<EntityUser> users = messageService.getInboxUsers();
		
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping(value = "/allMessagableUsers")
    public ResponseEntity<List<EntityUser>> getAllMessagableUsers() {
		List<EntityUser> users = messageService.getAllMessagableUsers();
		
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/getConversation")
    public ResponseEntity<List<Message>> getConversation(@RequestBody Long id) {
		List<Message> messages = messageService.getConversation(id);
		
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }


    @PostMapping(value="/new", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> newMessage(@RequestBody MessageDTO messageDTO) {

        if(messageService.newMessage(messageDTO) == 1)
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
		
    }
    

}