package com.Advertisement.Advertisement.service;

import com.Advertisement.Advertisement.dtos.AdvertisementDTO;
import com.Advertisement.Advertisement.model.*;
import com.Advertisement.Advertisement.dtos.*;
import com.Advertisement.Advertisement.repository.*;
import com.Advertisement.Advertisement.service.*;
import com.netflix.ribbon.RequestTemplate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import javax.servlet.http.HttpSession;

@Service
public class AdvertisementService {

	@Autowired
	AdvertisementRepository advertisementRepository;

	// @Autowired
	// UserRepository userRepository;

	@Autowired
	ReplyRepository replyRepository;

	@Autowired
	GradeService gradeService;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	BrandRepository brandRepository;

	@Autowired
	CarClassRepository carClassRepository;

	@Autowired
	ModelRepository modelRepository;

	@Autowired
	FuelTypeRepository fuelTypeRepository;

	@Autowired
	TransmissionTypeRepository transmissionTypeRepository;

	@Autowired
	RestTemplate restTemplate;

	// @Autowired
	// EndUserRepository endUserRepository;

	// @Autowired
	// AgentRepository agentRepository;

	// @Autowired
	// LoggerService loggerService;

	// @Autowired
	// SessionService sessionService;

	public Advertisement save(AdvertisementCreationDTO advertisementCreationDTO, Long id) {

		System.out.println("/////////////Advertisement service//////////////////////////////////////");
		System.out.println(id);
		System.out.println("///////////////////////////////////////////////////");

		Model model = modelRepository.findByName(advertisementCreationDTO.getModel());
		Brand brand = brandRepository.findByName(advertisementCreationDTO.getBrand());
		CarClass carClass = carClassRepository.findByName(advertisementCreationDTO.getCarClass());
		FuelType fuelType = fuelTypeRepository.findByName(advertisementCreationDTO.getFuelType());
		TransmissionType transmissionType = transmissionTypeRepository
				.findByName(advertisementCreationDTO.getTransType());

		System.out.println("///////////////////////");
		System.out.println(advertisementCreationDTO.getTransType());
		System.out.println(transmissionType);
		System.out.println("////////////////////////");

		Advertisement ad = new Advertisement(advertisementCreationDTO.getName(), model, brand, fuelType,
				transmissionType, carClass, advertisementCreationDTO.getTravelled(),
				advertisementCreationDTO.getCarSeats(), advertisementCreationDTO.getPrice(), id,
				advertisementCreationDTO.getDiscount(), advertisementCreationDTO.getPictures(), 0.0);

		// kada se kreira korisnik kreira mu se i korpa u koju ce moci da dodaje oglase!

		return advertisementRepository.save(ad);
	}

	public void saveImage(MultipartFile image) {

		String path = System.getProperty("user.dir");

		// String path=
		// C:\\Users\\Sherlock\\Desktop\\Mikroservisi\\Frontend\\src\\assets\\images

		System.out.println("EVO GA DIREKTORIJUM");
		String cwd = new File("").getAbsolutePath();
		System.out.println("OVO JE NOVA PUTANJA:=" + cwd);
		System.out.println("Putanja do direktorijuma je :" + path);

		// dodajte putanju do vaseg dir
		String newPath = path.replace("Advertisement", "Frontend\\src\\assets\\images");
		System.out.println("PRVI POKUSAJ=" + newPath);
		byte[] bytes;
		try {
			bytes = image.getBytes();
			Path put = Paths.get(newPath, image.getOriginalFilename());
			Files.write(put, bytes);
			System.out.println("UPISAO");
		} catch (IOException e) {

			e.printStackTrace();
			System.out.println("UPAO U EXCEPTION");
		}
	}

	public List<CarDetailsDTO> getCarDetails() {
		List<Brand> brands = brandRepository.findAll();
		List<CarClass> classes = carClassRepository.findAll();
		List<Model> models = modelRepository.findAll();
		List<FuelType> fuels = fuelTypeRepository.findAll();
		List<TransmissionType> transmisions = transmissionTypeRepository.findAll();

		List<CarDetailsDTO> details = new ArrayList<CarDetailsDTO>();
		CarDetailsDTO temp;

		for (Brand brand : brands) {
			temp = new CarDetailsDTO(brand.getName(), brand.getCode(), "BRAND");

			details.add(temp);
		}

		for (Model model : models) {
			temp = new CarDetailsDTO(model.getName(), model.getCode(), "CARMODEL");

			details.add(temp);
		}

		for (CarClass carclass : classes) {
			temp = new CarDetailsDTO(carclass.getName(), carclass.getCode(), "CARCLASS");

			details.add(temp);
		}

		for (FuelType fuelType : fuels) {
			temp = new CarDetailsDTO(fuelType.getName(), fuelType.getCode(), "FUELTYPE");

			details.add(temp);
		}

		for (TransmissionType transmission : transmisions) {
			temp = new CarDetailsDTO(transmission.getName(), transmission.getCode(), "GEARSHIFT");

			details.add(temp);
		}

		return details;
	}

	public Boolean saveCarDetail(CarDetailsDTO carDetailsDTO) {
		if (carDetailsDTO.getType().toLowerCase().equals("brand")) {
			Brand newItem = new Brand();
			newItem.setCode(carDetailsDTO.getCode());
			newItem.setName(carDetailsDTO.getName());
			brandRepository.save(newItem);
		} else if (carDetailsDTO.getType().toLowerCase().equals("carmodel")) {
			Model newItem = new Model();
			newItem.setCode(carDetailsDTO.getCode());
			newItem.setName(carDetailsDTO.getName());
			modelRepository.save(newItem);
		} else if (carDetailsDTO.getType().toLowerCase().equals("carclass")) {
			CarClass newItem = new CarClass();
			newItem.setCode(carDetailsDTO.getCode());
			newItem.setName(carDetailsDTO.getName());
			carClassRepository.save(newItem);
		} else if (carDetailsDTO.getType().toLowerCase().equals("fuel")) {
			FuelType newItem = new FuelType();
			newItem.setCode(carDetailsDTO.getCode());
			newItem.setName(carDetailsDTO.getName());
			fuelTypeRepository.save(newItem);
		} else if (carDetailsDTO.getType().toLowerCase().equals("gearshift")) {
			TransmissionType newItem = new TransmissionType();
			newItem.setCode(carDetailsDTO.getCode());
			newItem.setName(carDetailsDTO.getName());
			transmissionTypeRepository.save(newItem);
		} else {
			return false;
		}

		return true;
	}

	public Boolean deleteCarDetail(CarDetailsDTO carDetailsDTO) {
		if (carDetailsDTO.getType().toLowerCase().equals("brand")) {
			brandRepository.deleteByCode(carDetailsDTO.getCode());
		} else if (carDetailsDTO.getType().toLowerCase().equals("carmodel")) {
			modelRepository.deleteByCode(carDetailsDTO.getCode());
		} else if (carDetailsDTO.getType().toLowerCase().equals("carclass")) {
			carClassRepository.deleteByCode(carDetailsDTO.getCode());
		} else if (carDetailsDTO.getType().toLowerCase().equals("fueltype")) {
			fuelTypeRepository.deleteByCode(carDetailsDTO.getCode());
		} else if (carDetailsDTO.getType().toLowerCase().equals("GEARSHIFT")) {
			transmissionTypeRepository.deleteByCode(carDetailsDTO.getCode());
		} else {
			return false;
		}

		return true;
	}

	public List<AdvertisementCreationDTO> findAll() {
		List<Advertisement> advertisements = advertisementRepository.findAll();

		List<AdvertisementCreationDTO> sending = new ArrayList<AdvertisementCreationDTO>();
		AdvertisementCreationDTO temp;
		for (int i = 0; i < advertisements.size(); i++) {
			advertisements.get(i).setGrade(gradeService.calculateGradeForAd(advertisements.get(i).getId()));
			Advertisement tempad = advertisements.get(i);

			temp = new AdvertisementCreationDTO(tempad.getId(), tempad.getName(), tempad.getModel().getName(),
					tempad.getBrand().getName(), tempad.getFuelType().getName(), tempad.getTransmissionType().getName(),
					tempad.getCarClass().getName(), tempad.getTravelled(), tempad.getCarSeats(), tempad.getPrice(),
					tempad.getPostedByID(), tempad.getDiscount(), tempad.getPriceWithDiscount(), tempad.getPictures(),
					tempad.getGrade());

			sending.add(temp);
		}

		// String name, String model, String brand, String fuelType, String transType,
		// String carClass, int travelled,
		// int carSeats, double price, double discount, double priceWithDiscount,
		// ArrayList<String> pictures, double grade

		// loggerService.doLog("neka funkcija", "neki rezultat", "WARNING"); //TIPOVI
		// LOGOVA : WARNING, ERROR, INFO
		// loggerService.doLog("neka funkcija", "neki rezultat", "ERROR"); //FUNKCIJE :
		// NAPRAVIO OGLAS, POSLAO ZAHTEV ZA OGLAS, ODOBRIO ZAHTEV, OTKAZAO ZAHTEV, ODBIO
		// ZAHTEV, OBRISAO OGLAS
		// loggerService.doLog("neka funkcija", "neki rezultat", "INFO"); //REZULTATI:
		// ID OGLASA/NEUSPESNO, ID ZAHTEVA/NEUSPESNO, ID ZAHTEVA/NEUSPESNO, ID
		// ZAHTEVA/NEUSPESNO, ID OGLASA/NEUSPESNO

		return sending;
	}

	public List<AdvertisementCreationDTO> findAllByIds(ArrayList<Long> ids) {
		List<Advertisement> advertisements = advertisementRepository.findAll();
		List<AdvertisementCreationDTO> sending = new ArrayList<AdvertisementCreationDTO>();
		AdvertisementCreationDTO temp;

		for (Advertisement tempad : advertisements) {

			for (Long id : ids) {

				if (tempad.getId().equals(id)) {

					temp = new AdvertisementCreationDTO(tempad.getId(), tempad.getName(), tempad.getModel().getName(),
							tempad.getBrand().getName(), tempad.getFuelType().getName(),
							tempad.getTransmissionType().getName(), tempad.getCarClass().getName(),
							tempad.getTravelled(), tempad.getCarSeats(), tempad.getPrice(), tempad.getPostedByID(),
							tempad.getDiscount(), tempad.getPriceWithDiscount(), tempad.getPictures(),
							tempad.getGrade());

					sending.add(temp);
					break;
				}
			}

		}

		System.out.println("SALJEM TI OGLASA BR======================" + sending.size());

		return sending;
	}

	public Advertisement findOneByid(Long id) {
		Advertisement ad = advertisementRepository.findOneByid(id);

		ad.setGrade(gradeService.calculateGradeForAd(id));
		System.out.println(ad.getGrade());
		return advertisementRepository.findOneByid(id);
	}

	public Advertisement update(Advertisement advertisement) {
		return advertisementRepository.save(advertisement);
	}

	public List<AdvertisementCreationDTO> filterAds(FilterAdsDTO filterAdsDTO) {
		List<Advertisement> allAds = advertisementRepository.findAll();
		List<Advertisement> filteredAds = new ArrayList<Advertisement>();
		List<Advertisement> filteredAvailableAds = new ArrayList<Advertisement>();

		// Long id = restTemplate
		// .exchange("http://book/getAllBookings", HttpMethod.GET, null, new
		// ParameterizedTypeReference<Long>() {
		// }).getBody();

		// System.out.println("OVO JE OVO IZ BOOKA " + id);

		List<BookingDTO> bookedTimes = restTemplate.exchange("http://book/getAllBookings", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BookingDTO>>() {
				}).getBody();

		for (BookingDTO bookDTO : bookedTimes) {
			System.out.println("OVO SU BUKINZI =========================" + bookDTO.getTimeFrom() + "========"
					+ bookDTO.getTimeTo());
		}

		for (Advertisement ad : allAds) {
			if ((ad.getFuelType().getName() == filterAdsDTO.getFuelType() || filterAdsDTO.getFuelType() == null)
					&& (ad.getTransmissionType().getName() == filterAdsDTO.getTransmissionType()
							|| filterAdsDTO.getTransmissionType() == null)
					&& (ad.getCarClass().getName() == filterAdsDTO.getCarClass() || filterAdsDTO.getCarClass() == null)
					&& (ad.getTravelled() >= filterAdsDTO.getTravelledFrom() || filterAdsDTO.getTravelledFrom() == 0)
					&& (ad.getTravelled() <= filterAdsDTO.getTravelledTo() || filterAdsDTO.getTravelledTo() == 0)
					&& (ad.getFuelType().getName() == filterAdsDTO.getFuelType() || filterAdsDTO.getFuelType() == null)
					&& (ad.getPrice() >= filterAdsDTO.getPriceFrom() || filterAdsDTO.getPriceFrom() == 0)
					&& (ad.getPrice() <= filterAdsDTO.getPriceTo() || filterAdsDTO.getPriceTo() == 0)) {
				filteredAds.add(ad);
			}

		}

		int taken = 0;

		LocalDateTime timeFrom = filterAdsDTO.getTimeFrom();
		LocalDateTime timeTo = filterAdsDTO.getTimeTo();
		for (Advertisement ad : filteredAds) {
			taken = 0;
			if (filterAdsDTO.getTimeFrom() == null || filterAdsDTO.getTimeTo() == null) {
				for (BookingDTO bookingDTO : bookedTimes) {

					if (bookingDTO.getAdvertisementId() == ad.getId()) {
						if (filterAdsDTO.getTimeFrom() != null) {
							if (timeFrom.isAfter(bookingDTO.getTimeFrom())
									&& timeFrom.isBefore(bookingDTO.getTimeTo())) {
								taken = 1;
							}
						}

						if (filterAdsDTO.getTimeTo() != null) {
							if (timeTo.isAfter(bookingDTO.getTimeFrom()) && timeTo.isBefore(bookingDTO.getTimeTo())) {
								taken = 1;
							}
						}
					}
				}

			} else {
				for (BookingDTO bookingDTO : bookedTimes) {
					System.out.println("OVO JE ID ============ " + bookingDTO.getAdvertisementId());
					if (bookingDTO.getAdvertisementId() == ad.getId()) {

						if (timeFrom.isAfter(bookingDTO.getTimeFrom()) && timeFrom.isBefore(bookingDTO.getTimeTo())) {
							taken = 1;
						}

						if (timeTo.isAfter(bookingDTO.getTimeFrom()) && timeTo.isBefore(bookingDTO.getTimeTo())) {
							taken = 1;
						}

						if (bookingDTO.getTimeFrom().isAfter(timeFrom) && bookingDTO.getTimeTo().isBefore(timeFrom)) {
							taken = 1;
						}

						if (bookingDTO.getTimeFrom().isAfter(timeTo) && bookingDTO.getTimeTo().isBefore(timeTo)) {
							taken = 1;
						}
					}
				}

			}
			if (taken == 0) {
				filteredAvailableAds.add(ad);
			}
		}

		// KAD SE OTKOMENTARISE, VRACACE FILTEREDAVAILABLEADS

		List<AdvertisementCreationDTO> filteredAdsDTOs = new ArrayList<AdvertisementCreationDTO>();
		AdvertisementCreationDTO temp;
		for (Advertisement ad : filteredAvailableAds) {
			temp = new AdvertisementCreationDTO(ad);
			filteredAdsDTOs.add(temp);
		}

		return filteredAdsDTOs;
	}

	// public void saveCommentAndGrade(CommentDTO commentDTO){
	// Advertisement ad = advertisementRepository.findOneByid(commentDTO.getAd());
	// Grade grade = new Grade(commentDTO.getGrade(), ad); //OVDE TREBA DOBITI
	// ENDUSERA PO ID

	// gradeService.save(grade);

	// Optional obj = endUserRepository.findById(commentDTO.getUserId());

	// if(obj.isPresent()) {
	// Date date = new Date();
	// System.out.println(date);

	// Comment comment = new Comment(commentDTO.getMessage(), date, ad, (EndUser)
	// obj.get(), commentDTO.getGrade());

	// commentRepository.save(comment);
	// }
	// //sacuvaj komentar
	// }

	public AdvertisementCreationDTO findAdAndComments(Long id) {
		Advertisement ad = advertisementRepository.findOneByid(id); // OVA

		// List<Comment> db = commentRepository.findByAd_Id(id);

		// //sredjivanje komentara
		// List<CommentPreviewDTO> comments = new ArrayList<CommentPreviewDTO>();
		// for(int i = 0;i < db.size();i++) {
		// CommentPreviewDTO temp = new CommentPreviewDTO(db.get(i).getValue(),
		// db.get(i).getEndUser().getUser().getLoginInfo().getEmail(),
		// db.get(i).getGrade(), db.get(i).getDate());
		// temp.setId(db.get(i).getId());

		// if(db.get(i).getReply() != null) {
		// ReplyDTO replyDTO = new ReplyDTO();
		// replyDTO.setComment(db.get(i).getReply().getComment());
		// replyDTO.setAgentMail(db.get(i).getReply().getAgent().getUser().getLoginInfo().getEmail());

		// temp.setReplyDTO(replyDTO);
		// }
		// System.out.println(temp);

		// comments.add(temp);
		// }

		AdvertisementCreationDTO adDTO = new AdvertisementCreationDTO(ad);

		adDTO.setGrade(gradeService.calculateGradeForAd(id));

		// adDTO.setComments(comments);
		return adDTO;
	}

	public List<Advertisement> getAllByUser(Long id) {
		List<Advertisement> all = advertisementRepository.findAll();
		List<Advertisement> usersAds = new ArrayList<Advertisement>();
		for (Advertisement advertisement : all) {
			if (advertisement.getPostedByID().equals(id)) {
				usersAds.add(advertisement);
			}
		}
		return usersAds;
	}

	// public List<Long> getRentedCars(Long userId){
	// Optional obj = endUserRepository.findById(userId); //treba dobiti usera po id
	// List<Long> list = new ArrayList<>();
	// if(obj.isPresent()){
	// EndUser endUser = (EndUser) obj.get();

	// for(int i = 0;i < endUser.getRentedCars().size(); i++){
	// list.add(endUser.getRentedCars().get(i).getId());
	// }
	// }

	// return list;
	// }

	// public List<Advertisement> getAllByPostedBy(Long id) {
	// return advertisementRepository.findAllByPostedBy_Id(id);
	// }
	// Agent sadrzi polje User koje sadrzi polje login info koje sadrzi email. Tako
	// pronadji
	/*
	 * public void saveReply(ReplyDTO replyDTO){ Agent agent =
	 * agentRepository.findByLoginInfo_Email(replyDTO.getAgentMail()); Optional opt
	 * = commentRepository.findById(replyDTO.getId()); Reply reply = new
	 * Reply(replyDTO.getComment(), (Comment) opt.get(), agent); ((Comment)
	 * opt.get()).setReply(reply); replyRepository.save(reply);
	 * commentRepository.save((Comment) opt.get()); }
	 */
}