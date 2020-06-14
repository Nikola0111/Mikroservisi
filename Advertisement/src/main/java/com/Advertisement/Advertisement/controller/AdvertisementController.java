package com.Advertisement.Advertisement.controller;

import com.Advertisement.Advertisement.dtos.*;
import com.Advertisement.Advertisement.model.Advertisement;
import com.Advertisement.Advertisement.model.Comment;
import com.Advertisement.Advertisement.service.AdvertisementService;
import com.netflix.discovery.converters.Auto;
import com.netflix.eureka.registry.Key;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.ParameterizedTypeReference;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

@RestController
// @RequestMapping(value = "advertisement")
public class AdvertisementController {

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/hello")
	public ResponseEntity<?> get() {

		return new ResponseEntity<>(String.format("LOSELOSE nemanja piz di ce"), HttpStatus.OK);
	}

	@RequestMapping(value = "/saveImage", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ResponseEntity<Long> uploadImage(@RequestParam(value = "file", required = false) MultipartFile file)
			throws IOException {

		System.out.println("Pogodio");
		System.out.println(file.getOriginalFilename());

		advertisementService.saveImage(file);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<Long> save(@RequestBody AdvertisementDTO advertisementDTO) {

		System.out.println(advertisementDTO.getName() + advertisementDTO.getModel() + advertisementDTO.getBrand());
		System.out.println("AMIN");

		advertisementService.save(advertisementDTO);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/callMe", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<Long> getUserId(@RequestBody String kljuc)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		

		byte[] publicBytes = Base64.decodeBase64(kljuc);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = keyFactory.generatePublic(keySpec);


		System.out.println("PUBLIC KEY NA ADVERT JE="+pubKey);


		
	

        return new ResponseEntity<>(Long.valueOf(1), HttpStatus.OK);
    }

	

	

	//@PreAuthorize("hasAnyRole('ROLE_ENDUSER', 'ROLE_AGENT')")	
    @GetMapping(value = "/all")
    public ResponseEntity<List<Advertisement>> getAll() {
        List<Advertisement> advertisements = advertisementService.findAll();
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
	}

	@GetMapping(value = "/publicKey")
	public ResponseEntity publicKey() {
	
		System.out.println("Pogodio setovanje kljuca");

	

        return new ResponseEntity<>( HttpStatus.OK);
	}
	


	@PostMapping(value="/filterAdv", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Advertisement>> filterAds(@RequestBody FilterAdsDTO filterAdsDTO) {
		System.out.println("POGODIO");
		return new ResponseEntity<>(advertisementService.filterAds(filterAdsDTO), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,  consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdvertisementDTO> getAdvertisement(@PathVariable Long id) {
		Advertisement advertisement = advertisementService.findOneByid(id);
		if(advertisement == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new AdvertisementDTO(advertisement), HttpStatus.OK);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Advertisement> updateAdvertisement(@RequestBody Advertisement advertisement) throws Exception{
	    Advertisement ad = advertisementService.update(advertisement);
	    System.out.println(ad.getDiscount());
	    if(ad != null) {
	        return new ResponseEntity<>(advertisementService.findOneByid(ad.getId()), HttpStatus.OK);
        }
	    else {
	        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

	@PostMapping(value = "/saveCommentAndGrade", consumes = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Long> saveCommentAndGrade(@RequestBody CommentDTO commentDTO){
	//	advertisementService.saveCommentAndGrade(commentDTO);

		return new ResponseEntity<>((long) 1, HttpStatus.OK);

	}
/*
	@GetMapping(value = "/preview/{id}", produces = MediaType.APPLICATION_JSON_VALUE,  consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdvertisementDTO> getAdvertisementPreview(@PathVariable Long id) {
		AdvertisementDTO advertisementDTO = advertisementService.findAdAndComments(id);
		if(advertisementDTO == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(advertisementDTO.getGrade());
		return new ResponseEntity<>(advertisementDTO, HttpStatus.OK);
	}
	

	@GetMapping(value = "/getAllComments/{id}", produces = MediaType.APPLICATION_JSON_VALUE,  consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CommentPreviewDTO>> getAllComments(@PathVariable Long id) {
		System.out.println(id);
		AdvertisementDTO advertisementDTO = advertisementService.findAdAndComments(id);
		if(advertisementDTO == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(advertisementDTO.getName());
		System.out.println(advertisementDTO.getComments());
		return new ResponseEntity<>(advertisementDTO.getComments(), HttpStatus.OK);
	}
	


	@GetMapping(value = "/getRentedCars/{id}", produces = MediaType.APPLICATION_JSON_VALUE,  consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> getRentedCars(@PathVariable Long id) {
		List<Long> rentedCars = advertisementService.getRentedCars(id);
		return new ResponseEntity<>(rentedCars, HttpStatus.OK);
	}


	@GetMapping(value = "/getAllByPostedBy/{id}", produces = MediaType.APPLICATION_JSON_VALUE,  consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Advertisement>> getAllByPostedBy(@PathVariable Long id) {
		List<Advertisement> advertisements = advertisementService.getAllByPostedBy(id);
		return new ResponseEntity<>(advertisements, HttpStatus.OK);
	}

	@PostMapping(value = "/saveReply",  produces = MediaType.APPLICATION_JSON_VALUE,  consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> saveReply(@RequestBody ReplyDTO replyDTO){
		
		//KAD RESIS U SERVISU SAMO ODKOMENTARISI
		//advertisementService.saveReply(replyDTO);

		return new ResponseEntity<>((long) 1, HttpStatus.OK);
	}
	*/
}
