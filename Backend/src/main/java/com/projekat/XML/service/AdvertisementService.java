package com.projekat.XML.service;

import com.projekat.XML.dtos.*;
import com.projekat.XML.model.*;
import com.projekat.XML.model.requests.BookingRequest;
import com.projekat.XML.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import xml.projekat.com.javageneratedfiles.GetAdvertisementRequest;

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

	@Autowired
	FuelTypeRepository fuelTypeRepository;

	@Autowired
	CarClassRepository carClassRepository;

	@Autowired
	BrandRepository brandRepository;

	@Autowired
	ModelRepository modelRepository;

	@Autowired
	BookingRequestRepository bookingRequestRepository;

	@Autowired
	TransmissionTypeRepository transmissionTypeRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ReplyRepository replyRepository;

	@Autowired
	GradeService gradeService;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	EndUserRepository endUserRepository;

	@Autowired
	AgentRepository agentRepository;

	@Autowired
	UserService userService;

	public Advertisement save(AdvertisementCreationDTO advertisementCreationDTO) {

		Model model = modelRepository.findByName(advertisementCreationDTO.getModel());
		Brand brand = brandRepository.findByName(advertisementCreationDTO.getBrand());

		CarClass carClass = carClassRepository.findByName(advertisementCreationDTO.getCarClass());
		FuelType fuelType = fuelTypeRepository.findByName(advertisementCreationDTO.getFuelType());
		TransmissionType transmissionType = transmissionTypeRepository
				.findByName(advertisementCreationDTO.getTransType());

		Advertisement ad = new Advertisement(advertisementCreationDTO.getName(), model, brand, fuelType,
				transmissionType, carClass, advertisementCreationDTO.getTravelled(),
				advertisementCreationDTO.getCarSeats(), advertisementCreationDTO.getPrice(),
				userService.getLoggedUser(), advertisementCreationDTO.getDiscount(),
				advertisementCreationDTO.getPictures(), 0.0);

		return advertisementRepository.save(ad);
	}

	public void saveImage(MultipartFile image) {

		String path = System.getProperty("user.dir");
		System.out.println("Putanja do direktorijuma je :" + path);
		String newPath = path.replace("Backend", "Frontend\\src\\assets\\images");
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

	public List<Advertisement> findAll() {
		List<Advertisement> advertisements = advertisementRepository.findAll();
		for (int i = 0; i < advertisements.size(); i++) {
			advertisements.get(i).setGrade(gradeService.calculateGradeForAd(advertisements.get(i).getId()));
		}
		return advertisements;
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

	public List<Advertisement> filterAds(FilterAdsDTO filterAdsDTO) {
		List<Advertisement> allAds = advertisementRepository.findAll();
		List<Advertisement> filteredAds = new ArrayList<Advertisement>();
		List<Advertisement> filteredAvailableAds = new ArrayList<Advertisement>();

		List<BookingRequest> bookedTimes = bookingRequestRepository.findAll();

		for (Advertisement ad : allAds) {
			if ((ad.getTransmissionType().getName().equals(filterAdsDTO.getTransmissionType())
					|| filterAdsDTO.getTransmissionType() == null
					|| filterAdsDTO.getTransmissionType().equals("Choose a gearshift type"))
					&& (ad.getFuelType().getName().equals(filterAdsDTO.getFuelType())
							|| filterAdsDTO.getFuelType() == null
							|| filterAdsDTO.getFuelType().equals("Choose a fuel type"))
					&& (ad.getCarClass().getName().equals(filterAdsDTO.getCarClass())
							|| filterAdsDTO.getCarClass() == null
							|| filterAdsDTO.getCarClass().equals("Choose a car class"))
					&& (ad.getBrand().getName().equals(filterAdsDTO.getBrand()) || filterAdsDTO.getBrand() == null
							|| filterAdsDTO.getBrand().equals("Choose a car brand"))
					&& (ad.getModel().getName().equals(filterAdsDTO.getModel()) || filterAdsDTO.getModel() == null
							|| filterAdsDTO.getModel().equals("Choose a car model"))
					&& (ad.getCarSeats() == filterAdsDTO.getCarSeats() || filterAdsDTO.getCarSeats() == 0)
					&& (ad.getTravelled() >= filterAdsDTO.getTravelledFrom() || filterAdsDTO.getTravelledFrom() == 0)
					&& (ad.getTravelled() <= filterAdsDTO.getTravelledTo() || filterAdsDTO.getTravelledTo() == 0)
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
				return filteredAvailableAds;
			} else {

				if ((timeFrom.isBefore(LocalDateTime.now().plusDays(2))
						|| timeTo.isBefore(LocalDateTime.now().plusDays(2))) || timeFrom.isAfter(timeTo)) {
					return filteredAvailableAds;
				}
				for (BookingRequest booking : bookedTimes) {
					if (booking.getAdvertisement().getId() == ad.getId()) {

						if (timeFrom.isAfter(booking.getTimeFrom()) && timeFrom.isBefore(booking.getTimeTo())) {
							taken = 1;
						}

						if (timeTo.isAfter(booking.getTimeFrom()) && timeTo.isBefore(booking.getTimeTo())) {
							taken = 1;
						}

						if (booking.getTimeFrom().isAfter(timeFrom) && booking.getTimeFrom().isBefore(timeTo)) {
							taken = 1;
						}

						if (booking.getTimeTo().isAfter(timeFrom) && booking.getTimeTo().isBefore(timeTo)) {
							taken = 1;
						}
					}
				}

			}
			if (taken == 0) {
				filteredAvailableAds.add(ad);
			}
		}

		return filteredAvailableAds;
	}

	public void saveCommentAndGrade(CommentDTO commentDTO) {
		Advertisement ad = advertisementRepository.findOneByid(commentDTO.getAd());
		Grade grade = new Grade(commentDTO.getGrade(), ad);

		gradeService.save(grade);

		Optional obj = endUserRepository.findById(commentDTO.getUserId());

		if (obj.isPresent()) {
			Date date = new Date();
			System.out.println(date);

			Comment comment = new Comment(commentDTO.getMessage(), date, ad, (EndUser) obj.get(),
					commentDTO.getGrade());

			commentRepository.save(comment);
		}
		// sacuvaj komentar
	}

	public AdvertisementDTO findAdAndComments(Long id) {
		Advertisement ad = advertisementRepository.findOneByid(id);

		List<Comment> db = commentRepository.findByAd_Id(id);

		// sredjivanje komentara
		List<CommentPreviewDTO> comments = new ArrayList<CommentPreviewDTO>();
		for (int i = 0; i < db.size(); i++) {
			CommentPreviewDTO temp = new CommentPreviewDTO(db.get(i).getValue(),
					db.get(i).getEndUser().getUser().getLoginInfo().getEmail(), db.get(i).getGrade(),
					db.get(i).getDate());
			temp.setId(db.get(i).getId());

			if (db.get(i).getReply() != null) {
				ReplyDTO replyDTO = new ReplyDTO();
				replyDTO.setComment(db.get(i).getReply().getComment());
				replyDTO.setAgentMail(db.get(i).getReply().getAgent().getUser().getLoginInfo().getEmail());

				temp.setReplyDTO(replyDTO);
			}
			System.out.println(temp);

			comments.add(temp);
		}

		AdvertisementDTO adDTO = new AdvertisementDTO(ad);

		adDTO.setGrade(gradeService.calculateGradeForAd(id));

		adDTO.setComments(comments);
		return adDTO;
	}

	public List<Long> getRentedCars(Long userId) {
		Optional obj = endUserRepository.findById(userId);
		List<Long> list = new ArrayList<>();
		if (obj.isPresent()) {
			EndUser endUser = (EndUser) obj.get();

			for (int i = 0; i < endUser.getRentedCars().size(); i++) {
				list.add(endUser.getRentedCars().get(i).getId());
			}
		}

		return list;
	}

	public List<Advertisement> getAllByPostedBy(Long id) {
		return advertisementRepository.findAllByPostedBy_Id(id);
	}

	/*
	 * public void saveReply(ReplyDTO replyDTO){ Agent agent =
	 * agentRepository.findByLoginInfo_Email(replyDTO.getAgentMail()); Optional opt
	 * = commentRepository.findById(replyDTO.getId());
	 * 
	 * Reply reply = new Reply(replyDTO.getComment(), (Comment) opt.get(), agent);
	 * 
	 * ((Comment) opt.get()).setReply(reply);
	 * 
	 * replyRepository.save(reply);
	 * 
	 * commentRepository.save((Comment) opt.get()); }
	 */

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

	public List<CarDetailsDTO> getCarDetails() {
		List<Brand> brands = brandRepository.findAll();
		List<CarClass> classes = carClassRepository.findAll();
		List<Model> models = modelRepository.findAll();
		List<FuelType> fuels = fuelTypeRepository.findAll();
		List<TransmissionType> transmisions = transmissionTypeRepository.findAll();

		List<CarDetailsDTO> details = new ArrayList<CarDetailsDTO>();
		CarDetailsDTO temp;

		for (Brand brand : brands) {
			temp = new CarDetailsDTO(brand.getCode(), brand.getName(), "BRAND");

			details.add(temp);
		}

		for (Model model : models) {
			temp = new CarDetailsDTO(model.getCode(), model.getName(), "CARMODEL");

			details.add(temp);
		}

		for (CarClass carclass : classes) {
			temp = new CarDetailsDTO(carclass.getCode(), carclass.getName(), "CARCLASS");

			details.add(temp);
		}

		for (FuelType fuelType : fuels) {
			temp = new CarDetailsDTO(fuelType.getCode(), fuelType.getName(), "FUELTYPE");

			details.add(temp);
		}

		for (TransmissionType transmission : transmisions) {
			temp = new CarDetailsDTO(transmission.getCode(), transmission.getName(), "GEARSHIFT");

			details.add(temp);
		}

		return details;
	}


	public List<Advertisement> getAllByUser() {
		List<Advertisement> all = advertisementRepository.findAll();
		List<Advertisement> usersAds = new ArrayList<Advertisement>();
		Long userId = userService.getLoggedUserId();
		for (Advertisement advertisement : all) {
			if (advertisement.getPostedBy().getId().equals(userId)) {
				usersAds.add(advertisement);
			}
		}
		return usersAds;
	}

	public String saveSoapAdvertisement(GetAdvertisementRequest request){
		EntityUser entityUser = userRepository.findOneByid(request.getAdvertisement().getUserID());

		Model model = modelRepository.findOneByid(request.getAdvertisement().getModel().getId());
		Brand brand = brandRepository.findOneByid(request.getAdvertisement().getModel().getId());
		FuelType fueltype = fuelTypeRepository.findOneByid(request.getAdvertisement().getModel().getId());
		TransmissionType transmissionType = transmissionTypeRepository.findOneByid(request.getAdvertisement().getModel().getId());
		CarClass carClass = carClassRepository.findOneByid(request.getAdvertisement().getModel().getId());

		Advertisement advertisement = new Advertisement(request.getAdvertisement().getName(),
				model, brand, fueltype, transmissionType, carClass, request.getAdvertisement().getTravelled(), request.getAdvertisement().getCarSeats(),
				request.getAdvertisement().getPrice(), entityUser, 10.0,new ArrayList<String>(), 0.0);

		advertisementRepository.save(advertisement);

		return "saved";
	}

}
