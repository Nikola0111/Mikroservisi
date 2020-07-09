package com.AthorizationAndAuthentication.AthorizationAndAuthentication.controller;

import com.AthorizationAndAuthentication.AthorizationAndAuthentication.dtos.AgentDTO;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.service.AgentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
public class AgentController {

    @Autowired
    private AgentService agentService;

    // @GetMapping(value = "/checkPasswordChanged", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<Boolean> checkPasswordChanged(){
    //     return new ResponseEntity(agentService.checkPasswordChanged(), HttpStatus.OK);
    // }

    
    @PostMapping(value = "/getAgentIDByUserID", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getAgentIDByUserID(@RequestBody Long id){
        return new ResponseEntity<>(agentService.getAgentIDByUserID(id), HttpStatus.OK);
    }

    @PostMapping(value = "/registerAgent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> registerAgent(@RequestBody AgentDTO agentDTO){
        Integer ret = agentService.registerAgent(agentDTO);

        if(ret != 0){
            return new ResponseEntity<>(ret, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
    }
}