package com.AthorizationAndAuthentication.AthorizationAndAuthentication.controller;

import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.EndUser;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.EntityUser;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.LoginInfo;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.service.AgentService;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.service.LoginInfoService;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.service.SessionService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

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
    private LoginInfoService loginInfoService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    KeyPairClassService keyPairClassService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/hello")
    public ResponseEntity<?> get() {

        return new ResponseEntity<>(String.format("NEMANJA TI SI GOVNOI i jebi se i pusi "), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public String Login() {

        System.out.println("PROSO PROSO");

        String authorizationHeader = keyPairClassService.getPublicKey().toString();

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.add("publicKeyAttribute", authorizationHeader);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<?> responseEntity = restTemplate.exchange("http://advertisement/publicKey", HttpMethod.GET,
                requestEntity, new ParameterizedTypeReference<ResponseEntity<?>>() {
                }).getBody();

        return "proslo";
    }

    @GetMapping(value = "/getPublicKey")
    public ResponseEntity<String> getPublicKey() {

        System.out.println("OVO JE poslati PUBLIC KEY=" + keyPairClassService.getPublicKey().toString());

        return new ResponseEntity<>(keyPairClassService.getPublicKey().toString(), HttpStatus.OK);
    }

    @GetMapping(value = "/loginToken")
    public ResponseEntity getToken() {

        System.out.println("POGODIO JE LOGIN TOKEN");

      
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "/dodajUsere")
    public ResponseEntity addUsers() {

        System.out.println("POGODIO JE ADD USERS");

      
       userService.saveAdmin();
       userService.saveAgent();
       userService.saveEndUser();

        return new ResponseEntity<>(HttpStatus.OK);
    }
    

    @GetMapping(value = "/logout")
    public ResponseEntity logOut(HttpServletRequest request) {

        sessionService.invalidateSession();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getUserId")
    public ResponseEntity<Long> getUserId() {

        return new ResponseEntity<>(userService.getLoggedUserId(), HttpStatus.OK);
    }

    @GetMapping("/getCsrf")
    public ResponseEntity<?> getXsrf() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getLoggedEndUser")
    public ResponseEntity<EndUser> getLoggedEndUser() {

        return new ResponseEntity<>(userService.getLoggedEndUser(), HttpStatus.OK);
    }

    @GetMapping(value = "/getUserByUsername/{username}")
    public ResponseEntity<EntityUser> getUserByUsername(@PathVariable("username") String username) {

        EntityUser user = userService.findByUsername(username);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/increaseEndUsersNumberOfAds")
    public ResponseEntity<Long> increaseEndUsersNumberOfAds() {

        return new ResponseEntity<>(userService.increaseEndUsersNumberOfAds(), HttpStatus.OK);
    }

    // @PreAuthorize("hasAuthority('user:read')")
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<EntityUser>> getAllUsers() {

        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);

    }

    // @PreAuthorize("hasAuthority('user:read')")
    @PostMapping(value = "/getAgentIDByMail", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Long> getAgentIDByMail(@RequestBody String email) {
        return new ResponseEntity<>(agentService.getAgentIDByEmail(email), HttpStatus.OK);
    }

    // @PreAuthorize("hasAuthority('user:read')")
    @PostMapping(value = "/getAgentEmail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getAgentMail(@RequestBody Long id) {
        return new ResponseEntity<>(agentService.getAgentMail(id), HttpStatus.OK);
    }

    // @PostMapping(value = "/passwordChange", produces =
    // MediaType.APPLICATION_JSON_VALUE, consumes =
    // MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<Long> changePassword(@RequestBody UserDTO userDTO){
    // userService.changePassword(userDTO.getJmbg(), userDTO.getPassword());

    // return new ResponseEntity<Long>((long) 1, HttpStatus.OK);
    // }

}
