package com.Advertisement.Advertisement.controller;

import com.Advertisement.Advertisement.dtos.*;
import com.Advertisement.Advertisement.model.Advertisement;
import com.Advertisement.Advertisement.model.Comment;
import com.Advertisement.Advertisement.service.AdvertisementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdvertisementController {

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/hello")
	public ResponseEntity<?> get() {

		return new ResponseEntity<>(String.format("LOSELOSE"), HttpStatus.OK);
	}

	@RequestMapping(value = "/saveImage", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ResponseEntity<Long> uploadImage(@RequestParam(value = "file", required = false) MultipartFile file)
			throws IOException {

		System.out.println("Pogodio JE OVAJ saveImage");
		System.out.println(file.getOriginalFilename());

		advertisementService.saveImage(file);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<Integer> save(@RequestBody AdvertisementCreationDTO advertisementCreationDTO) {
		Long id;

		System.out.println("Pogodio je ovaj SAVE");
		id = restTemplate.exchange("http://auth/getUserId", HttpMethod.GET, null, new ParameterizedTypeReference<Long>() {
			}).getBody();

		HttpEntity<Long> request = new HttpEntity<>(id);
		////////////////////////
		Integer ret = restTemplate.postForEntity("http://auth/getNumberOfPostedAds", request,Integer.class, id).getBody();
		
		if(ret == 3){
			return new ResponseEntity<>(ret, HttpStatus.OK);
		}

		restTemplate.postForEntity("http://auth/increaseNumberOfAdsPosted", request,Long.class, id);
        
		advertisementService.save(advertisementCreationDTO, id);

		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	// @PreAuthorize("hasAnyRole('ROLE_ENDUSER', 'ROLE_AGENT')")
	@GetMapping(value = "/all")
	public ResponseEntity<List<AdvertisementCreationDTO>> getAll() {
		List<AdvertisementCreationDTO> advertisements = advertisementService.findAll();
		return new ResponseEntity<>(advertisements, HttpStatus.OK);
	}

	@GetMapping(value = "/getAllAdvertisementsForCart")
	public ResponseEntity<List<AdvertisementCreationDTO>> getAllBookings() {

		/*
		 * List<AdvertisementCreationDTO> advertisementsForCart=new
		 * ArrayList<AdvertisementCreationDTO>();
		 * 
		 * for (AdvertisementCreationDTO advertisement : advertisementService.findAll())
		 * {
		 * 
		 * for (Long id : ids) {
		 * 
		 * if(advertisement.getId().equals(id)){
		 * 
		 * advertisementsForCart.add(advertisement); }
		 * 
		 * }
		 * 
		 * }
		 */

		return new ResponseEntity<>(advertisementService.findAll(), HttpStatus.OK);
	}

	@PostMapping(value = "/getAllAdvertisementsForCart2")
	public ResponseEntity<List<AdvertisementCreationDTO>> getAllBookings2(@RequestBody ArrayList<Long> ids) {

		/*
		 * List<AdvertisementCreationDTO> advertisementsForCart=new
		 * ArrayList<AdvertisementCreationDTO>();
		 * 
		 * for (AdvertisementCreationDTO advertisement : advertisementService.findAll())
		 * {
		 * 
		 * for (Long id : ids) {
		 * 
		 * if(advertisement.getId().equals(id)){
		 * 
		 * advertisementsForCart.add(advertisement); }
		 * 
		 * }
		 * 
		 * }
		 */

		return new ResponseEntity<>(advertisementService.findAll(), HttpStatus.OK);
	}

	@PostMapping(value = "/allByIds", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AdvertisementCreationDTO>> getAllByIds(@RequestBody ArrayList<Long> ids) {

		return new ResponseEntity<>(advertisementService.findAllByIds(ids), HttpStatus.OK);
	}

	@PostMapping(value = "/filterAdv", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AdvertisementCreationDTO>> filterAds(@RequestBody FilterAdsDTO filterAdsDTO) {
		System.out.println("POGODIO");
		return new ResponseEntity<>(advertisementService.filterAds(filterAdsDTO), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdvertisementDTO> getAdvertisement(@PathVariable Long id) {
		Advertisement advertisement = advertisementService.findOneByid(id);
		if (advertisement == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new AdvertisementDTO(advertisement), HttpStatus.OK);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Advertisement> updateAdvertisement(@RequestBody Advertisement advertisement)
			throws Exception {
		Advertisement ad = advertisementService.update(advertisement);
		System.out.println(ad.getDiscount());
		if (ad != null) {
			return new ResponseEntity<>(advertisementService.findOneByid(ad.getId()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PostMapping(value = "/saveCommentAndGrade", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> saveCommentAndGrade(@RequestBody CommentDTO commentDTO) {
		// advertisementService.saveCommentAndGrade(commentDTO);

		return new ResponseEntity<>((long) 1, HttpStatus.OK);

	}

	@GetMapping(value = "/getAllDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CarDetailsDTO>> getAllDetails() {
		List<CarDetailsDTO> cardetails = advertisementService.getCarDetails();

		return new ResponseEntity<>(cardetails, HttpStatus.OK);
	}

	@PostMapping(value = "/saveCarDetail", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> saveCarDetail(@RequestBody CarDetailsDTO carDetailsDTO) {
		Boolean ret = advertisementService.saveCarDetail(carDetailsDTO);

		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	@PostMapping(value = "/deleteCarDetail", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> deleteCarDetail(@RequestBody CarDetailsDTO carDetailsDTO) {
		Boolean ret = advertisementService.deleteCarDetail(carDetailsDTO);

		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	// @GetMapping(value = "/getRentedCars/{id}", produces =
	// MediaType.APPLICATION_JSON_VALUE, consumes= MediaType.APPLICATION_JSON_VALUE)
	// public ResponseEntity<List<Long>> getRentedCars(@PathVariable Long id) {
	// List<Long> rentedCars = advertisementService.getRentedCars(id);
	// return new ResponseEntity<>(rentedCars, HttpStatus.OK);
	// }

	@GetMapping(value = "/preview/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdvertisementCreationDTO> getAdvertisementPreview(@PathVariable Long id) {
		AdvertisementCreationDTO advertisementDTO = advertisementService.findAdAndComments(id);
		if (advertisementDTO == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(advertisementDTO.getGrade());
		return new ResponseEntity<>(advertisementDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/getAllByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Advertisement>> getAllByUser() {
        Long id = restTemplate
                .exchange("http://auth/getUserId", HttpMethod.GET, null, new ParameterizedTypeReference<Long>() {
                }).getBody();

        List<Advertisement> ads = advertisementService.getAllByUser(id);

        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

	/*
	 * 
	 * @GetMapping(value = "/getAllComments/{id}", produces =
	 * MediaType.APPLICATION_JSON_VALUE, consumes= MediaType.APPLICATION_JSON_VALUE)
	 * public ResponseEntity<List<CommentPreviewDTO>> getAllComments(@PathVariable
	 * Long id) { System.out.println(id); AdvertisementDTO advertisementDTO =
	 * advertisementService.findAdAndComments(id); if(advertisementDTO == null) {
	 * return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
	 * System.out.println(advertisementDTO.getName());
	 * System.out.println(advertisementDTO.getComments()); return new
	 * ResponseEntity<>(advertisementDTO.getComments(), HttpStatus.OK); }
	 * 
	 * @GetMapping(value = "/getAllByPostedBy/{id}", produces =
	 * MediaType.APPLICATION_JSON_VALUE, consumes= MediaType.APPLICATION_JSON_VALUE)
	 * public ResponseEntity<List<Advertisement>> getAllByPostedBy(@PathVariable
	 * Long id) { List<Advertisement> advertisements =
	 * advertisementService.getAllByPostedBy(id); return new
	 * ResponseEntity<>(advertisements, HttpStatus.OK); }
	 * 
	 * @PostMapping(value = "/saveReply", produces =
	 * MediaType.APPLICATION_JSON_VALUE, consumes= MediaType.APPLICATION_JSON_VALUE)
	 * public ResponseEntity<Long> saveReply(@RequestBody ReplyDTO replyDTO){
	 * 
	 * //KAD RESIS U SERVISU SAMO ODKOMENTARISI
	 * //advertisementService.saveReply(replyDTO);
	 * 
	 * return new ResponseEntity<>((long) 1, HttpStatus.OK); }
	 */
}
