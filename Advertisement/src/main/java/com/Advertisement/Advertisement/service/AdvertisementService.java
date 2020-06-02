package com.Advertisement.Advertisement.service;

import com.Advertisement.Advertisement.dtos.AdvertisementDTO;
import com.Advertisement.Advertisement.model.*;
import com.Advertisement.Advertisement.dtos.*;
import com.Advertisement.Advertisement.repository.*;
import com.Advertisement.Advertisement.service.*;
import com.netflix.ribbon.RequestTemplate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
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
	RestTemplate restTemplate;

	// @Autowired
	// EndUserRepository endUserRepository;

	// @Autowired
	// AgentRepository agentRepository;

	// @Autowired
	// LoggerService loggerService;

	// @Autowired
	// SessionService sessionService;

	public Advertisement save(AdvertisementDTO advertisementDTO) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		// Long id = (Long) session.getAttribute("user");

		Long id = restTemplate.getForObject("http://localhost:8080/getUserId", Long.class);

		Advertisement ad = new Advertisement(advertisementDTO.getName(), advertisementDTO.getModel(),
				advertisementDTO.getBrand(), advertisementDTO.getFuelType(), advertisementDTO.getTransmissionType(),
				advertisementDTO.getCarClass(), advertisementDTO.getTravelled(), advertisementDTO.getCarSeats(),
				advertisementDTO.getPrice(), id, advertisementDTO.getDiscount(), advertisementDTO.getPictures(), 0.0);

		// kada se kreira korisnik kreira mu se i korpa u koju ce moci da dodaje oglase!

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

		// loggerService.doLog("neka funkcija", "neki rezultat", "WARNING"); //TIPOVI
		// LOGOVA : WARNING, ERROR, INFO
		// loggerService.doLog("neka funkcija", "neki rezultat", "ERROR"); //FUNKCIJE :
		// NAPRAVIO OGLAS, POSLAO ZAHTEV ZA OGLAS, ODOBRIO ZAHTEV, OTKAZAO ZAHTEV, ODBIO
		// ZAHTEV, OBRISAO OGLAS
		// loggerService.doLog("neka funkcija", "neki rezultat", "INFO"); //REZULTATI:
		// ID OGLASA/NEUSPESNO, ID ZAHTEVA/NEUSPESNO, ID ZAHTEVA/NEUSPESNO, ID
		// ZAHTEVA/NEUSPESNO, ID OGLASA/NEUSPESNO

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

		List<BookingDTO> bookedTimes = restTemplate.exchange("http://booking/getAllBookings", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BookingDTO>>() {
				}).getBody();

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

					if (bookingDTO.getAdvertisementID() == ad.getId()) {
						if (filterAdsDTO.getTimeFrom() != null) {
							if (timeFrom.isAfter(bookingDTO.getStartTime())
									&& timeFrom.isBefore(bookingDTO.getEndTime())) {
								taken = 1;
							}
						}

						if (filterAdsDTO.getTimeTo() != null) {
							if (timeTo.isAfter(bookingDTO.getStartTime()) && timeTo.isBefore(bookingDTO.getEndTime())) {
								taken = 1;
							}
						}
					}
				}
			}
			if (taken == 0) {
				filteredAvailableAds.add(ad);
			}
		}

		// KAD SE OTKOMENTARISE, VRACACE FILTEREDAVAILABLEADS
		return filteredAvailableAds;
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

	// public AdvertisementDTO findAdAndComments(Long id) {
	// Advertisement ad = advertisementRepository.findOneByid(id); //OVA

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

	// AdvertisementDTO adDTO = new AdvertisementDTO(ad);

	// adDTO.setGrade(gradeService.calculateGradeForAd(id));

	// adDTO.setComments(comments);
	// return adDTO;
	// }

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
}
