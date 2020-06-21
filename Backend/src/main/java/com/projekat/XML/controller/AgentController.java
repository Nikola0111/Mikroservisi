package com.projekat.XML.controller;

import com.projekat.XML.dtos.AgentDTO;
import com.projekat.XML.dtos.CarDTO;
import com.projekat.XML.dtos.CarReportDTO;
import com.projekat.XML.dtos.UserDTO;
import com.projekat.XML.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;


@RestController
@RequestMapping(value = "agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> register(@RequestBody AgentDTO agentDTO){
        int value = agentService.save(agentDTO);

        if(value == 0){
            return new ResponseEntity(value, HttpStatus.OK);
        }

        return new ResponseEntity(value, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/checkPasswordChanged", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkPasswordChanged(){
        return new ResponseEntity(agentService.checkPasswordChanged(), HttpStatus.OK);
    }

    @GetMapping(value = "/getOwnersCars", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarDTO>> getOwnersCars() {
        List<CarDTO> cars = agentService.findOwnersCars();
        return new ResponseEntity<List<CarDTO>>(cars, HttpStatus.OK);
    }

    @PostMapping(value = "/saveReport", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveReport(@RequestBody CarReportDTO carReport){
        agentService.saveReport(carReport);

        return new ResponseEntity(HttpStatus.OK);
    }
}
