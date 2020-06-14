package com.AthorizationAndAuthentication.AthorizationAndAuthentication.controller;

import com.AthorizationAndAuthentication.AthorizationAndAuthentication.dtos.UserDTO;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.EntityUser;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.service.KeyPairClassService;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.service.UserService;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Key;



@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    KeyPairClassService keyPairClassService;


    @GetMapping("/hello")
    public ResponseEntity<?> get() {
       
		
        return new ResponseEntity<>(String.format("NEMANJA TI SI GOVNOI i jebi se i pusi "), HttpStatus.OK);
    }



    
    @PostMapping(value = "/login")
    public String Login(){


        System.out.println("PROSO PROSO");

        String authorizationHeader =keyPairClassService.getPublicKey().toString();


        HttpHeaders requestHeaders = new HttpHeaders();
        
        requestHeaders.add("publicKeyAttribute", authorizationHeader);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<?> responseEntity = restTemplate.exchange(
            "http://advertisement/publicKey",
            HttpMethod.GET,
            requestEntity,
            new ParameterizedTypeReference<ResponseEntity<?>>() {
            }).getBody();



      return "proslo";
    }

    
	@GetMapping(value = "/getPublicKey")
    public ResponseEntity<String> getUserId() {



		System.out.println("OVO JE poslati PUBLIC KEY="+keyPairClassService.getPublicKey().toString());

        return new ResponseEntity<>(keyPairClassService.getPublicKey().toString(), HttpStatus.OK);
    }


    @GetMapping(value = "/loginToken")
    public ResponseEntity getToken() {
    
        System.out.println("POGODIO JE LOGIN TOKEN");



        String publicK = Base64.encodeBase64String(keyPairClassService.getPublicKey().getEncoded());

        System.out.println("PUBLIC KEY JE NA AUTH="+keyPairClassService.getPublicKey());

        HttpEntity<String> request = new HttpEntity<>(publicK);

        restTemplate
        .exchange("http://advert/callMe", HttpMethod.POST, request, new ParameterizedTypeReference<Long>() {
        }).getBody();

     /*   String authorizationHeader =keyPairClassService.getPublicKey().toString();


        HttpHeaders requestHeaders = new HttpHeaders();

      
        requestHeaders.setContentType(MediaType.TEXT_PLAIN);
        
        requestHeaders.add("publicKeyAttribute", authorizationHeader);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<?> responseEntity = restTemplate.exchange(
            "http://advert/publicKey",
            HttpMethod.GET,
            requestEntity,
            new ParameterizedTypeReference<ResponseEntity<?>>() {
            }).getBody();
*/
        return new ResponseEntity<>(HttpStatus.OK);
    }

/*
    @PostMapping(value = "/login", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityUser> Login(@RequestBody EntityUser user){

        System.out.println(user.getLoginInfo().getEmail() + " " + user.getLoginInfo().getPassword());

        //provera email-a i username-a:
        EntityUser userDB = userService.findUserByEmailAndPassword(user);
        if(userDB == null){
            System.out.println("Wrong username or password");
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }


        userService.saveUser(userDB);

        return new ResponseEntity<>(userDB, HttpStatus.OK);
    } */

    @GetMapping(value = "/logout")
    public ResponseEntity getAllForCart() {
      
        
        System.out.println("izbrisao sve proslo izlogovan");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // @PostMapping(value = "/passwordChange", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<Long> changePassword(@RequestBody UserDTO userDTO){
    //     userService.changePassword(userDTO.getJmbg(), userDTO.getPassword());

    //     return new ResponseEntity<Long>((long) 1, HttpStatus.OK);
    // }

}
