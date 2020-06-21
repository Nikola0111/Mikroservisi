package com.projekat.XML.controller;

import com.projekat.XML.dtos.CarDetailsDTO;
import com.projekat.XML.model.CarDetails;
import com.projekat.XML.service.CarDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping(value = "cardetails")
public class CarDetailsController {

    @Autowired
    private CarDetailsService carDetailsService;

    @GetMapping(value = "getAllDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarDetails>> getAllDetails(){
        List<CarDetails> carDetails = carDetailsService.getAll();

        return new ResponseEntity<>(carDetails, HttpStatus.OK);
    }

    @PostMapping(value = "save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> save(@RequestBody CarDetailsDTO carDetailsDTO){
        System.out.println(carDetailsDTO);
        Integer i = carDetailsService.save(carDetailsDTO);
        System.out.println(i);
        if(i == 0){
            return new ResponseEntity<>(i, HttpStatus.OK);
        }

        return new ResponseEntity<>(i, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "delete/{code}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> delete(@PathVariable("code") String code){
        System.out.println(code);
        carDetailsService.delete(code);
        Integer i = 1;


        return new ResponseEntity<>(i, HttpStatus.OK);
    }
}
