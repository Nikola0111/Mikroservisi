package com.Advertisement.Advertisement.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.Advertisement.Advertisement.dtos.CommentDTO;
import com.Advertisement.Advertisement.dtos.CommentPreviewDTO;
import com.Advertisement.Advertisement.dtos.EndUserNumberOfAdsDTO;
import com.Advertisement.Advertisement.dtos.ReplyDTO;
import com.Advertisement.Advertisement.model.Advertisement;
import com.Advertisement.Advertisement.service.AdvertisementService;
import com.Advertisement.Advertisement.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

@RestController
public class CommentGradeController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/getAllByPostedBy/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Advertisement>> getAllByPostedBy(@PathVariable Long id) {
        List<Advertisement> advertisements = advertisementService.getAllByPostedBy(id);
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @PostMapping(value = "/saveReply", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveReply(@RequestBody ReplyDTO replyDTO) {
        advertisementService.saveReply(replyDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/saveCommentAndGrade", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> saveCommentAndGrade(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");

        HttpEntity<String> entity = sessionService.makeAuthorizationHeader(authorization);

        EndUserNumberOfAdsDTO endUser = restTemplate.exchange("http://auth/getLoggedEndUser", HttpMethod.GET, entity,
                new ParameterizedTypeReference<EndUserNumberOfAdsDTO>() {
                }).getBody();

        advertisementService.saveCommentAndGrade(commentDTO, endUser.getId());

        return new ResponseEntity<>((long) 1, HttpStatus.OK);

    }

    @GetMapping(value = "/getUnapprovedComments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommentPreviewDTO>> getUnapprovedComments() {
        return new ResponseEntity<>(advertisementService.getUnapprovedComments(), HttpStatus.OK);
    }

    @PostMapping(value = "/approve/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> approve(@PathVariable("id") Long id) {
        System.out.println(id + " Id komentara");
        advertisementService.approveComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        advertisementService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}