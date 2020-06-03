package com.Booking.Booking.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.Booking.Booking.dtos.AdvertisementCreationDTO;
import com.Booking.Booking.dtos.BookingRequestFrontDTO;
import com.Booking.Booking.dtos.ItemInCartDTO;
import com.Booking.Booking.dtos.ReservationDTO;
import com.Booking.Booking.enums.RequestStates;
import com.Booking.Booking.model.requests.BookingRequest;
import com.Booking.Booking.repository.BookingRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.core.ParameterizedTypeReference;

@Service
public class BookingRequestService {

    @Autowired
    BookingRequestRepository bookingRequestRepository;

    @Autowired
    RestTemplate restTemplate;

   
//PUCACE JER LogedUserId MORA DA SE DOBAVI NEKAKO


    public void makeRequests(List<ItemInCartDTO> listaZahteva){
        //pretrazujem za svakog vlasnika, ako se pogodi da je isti, pogledam dal ide oglas odvojeno ili zajedno i na osnovu toga 
        //ubacujem u listu, cuvam i obrisem, onda se predje na sledeceg vlasnika
        //EROR STA AKO JE U PRVOJ LISTI OPET ISTI
        List<Long> vlasnici=new ArrayList<Long>();
        List<ItemInCartDTO> copyList=listaZahteva;
        ArrayList<ItemInCartDTO> together=new ArrayList<>();
        ArrayList<ItemInCartDTO> seprate=new ArrayList<>();


        for (ItemInCartDTO ItemInCartDTO : listaZahteva) {
            
            if(!vlasnici.contains(ItemInCartDTO.getAdvertisementPostedById())){

                vlasnici.add(ItemInCartDTO.getAdvertisementPostedById());

            }
        }

        System.out.println("Broj vlasnika je : "+vlasnici.size());


        for (Long id: vlasnici) {
            
            for (ItemInCartDTO adv :copyList) {
                
                if(adv.getAdvertisementPostedById().equals(id)){

                    if(adv.isTogether()){


                        together.add(adv);

                    }
                    else{
                        seprate.add(adv);

                    }

                }



            }

            // treba ovde da sejvujem ako nisu prazne i da ih ispraznim nakon sejvanja
           BookingRequest request=bookingRequestRepository.findTopByOrderByIdDesc();
           Long lastGroupId;
         
           System.out.println(request);
            
            if(request==null){
                lastGroupId=Long.valueOf(0);

            }
            else{
                lastGroupId=request.getGroupId();
            }

            // try{
            //     lastGroupId=request.getGroupId();
               

            // }
            // catch(Exception e){
            //     lastGroupId=Long.valueOf(0);
              
            // }

           

            System.out.println("Poslednji id je : "+lastGroupId);
            
            if(!seprate.isEmpty()){

                for (ItemInCartDTO ItemInCartDTO2 : seprate) {
                
                lastGroupId++;
                bookingRequestRepository.save(new BookingRequest( ItemInCartDTO2.getAdvertisementPostedById(),getLogedUserId(),
                 lastGroupId, RequestStates.PENDING,ItemInCartDTO2.getAdvertisementId() , ItemInCartDTO2.isTogether(),ItemInCartDTO2.getTimeFrom(),ItemInCartDTO2.getTimeTo()));



                 
                }
                seprate.clear();

               

            }


           lastGroupId++;

            if(!together.isEmpty()){

                for (ItemInCartDTO ItemInCartDTO : together) {

                    //GET LoggedUserId MORA DA SE DOBAVI NEKAKO ZATO PUCAA
                    
                    bookingRequestRepository.save(new BookingRequest( ItemInCartDTO.getAdvertisementPostedById(),getLogedUserId(),
                    lastGroupId, RequestStates.PENDING,ItemInCartDTO.getAdvertisementId(),
                     ItemInCartDTO.isTogether(),ItemInCartDTO.getTimeFrom(),ItemInCartDTO.getTimeTo()));


                }

                together.clear();
            }


           

        }

        
        
    }


    public List<BookingRequest> getAllForRenter(){
       
        return bookingRequestRepository.findByUserForId(getLogedUserId());
    }

    public void cancelRequest(Long group){

        for (BookingRequest request : bookingRequestRepository.findAllByGroupId(group)) {
            
            request.setStateOfRequest(RequestStates.CANCELED);
            bookingRequestRepository.save(request);
        }

    }

   

   

  

    public List<BookingRequest> getAllSpecificForAgent(RequestStates state){
        List<BookingRequest> needed=new ArrayList<BookingRequest>();
 
 
        for (BookingRequest bookingRequest : bookingRequestRepository.findByUserForId(getLogedUserId())) {
            
             
 
 
             if(bookingRequest.getStateOfRequest().equals(state)){
                 needed.add(bookingRequest);
             }
 
        }
         return needed;
     }


     public List<Long> getSpecificGroupsForAgent (RequestStates state){
        List<Long> group=new ArrayList<Long>();
        System.out.println(getLogedUserId());
        
        for (BookingRequest bookingRequest : bookingRequestRepository.findByUserForId(getLogedUserId())) {
            
           

            if(!group.contains(bookingRequest.getGroupId())){
               
                if(bookingRequest.getStateOfRequest().equals(state)){
                   
                group.add(bookingRequest.getGroupId());
                }
            }
        }

        return group;


    }

    public List<BookingRequestFrontDTO> getAllSpecificForBuyer(RequestStates state){
        List<BookingRequest> needed=new ArrayList<BookingRequest>();
        List<BookingRequestFrontDTO> forFront=new ArrayList<BookingRequestFrontDTO>();
 
        List<AdvertisementCreationDTO> advertisements = restTemplate.exchange("http://advert/getAllAdvertisementsForCart", HttpMethod.GET, null,
        new ParameterizedTypeReference<List<AdvertisementCreationDTO>>() {
        }).getBody();



        for (BookingRequest bookingRequest : bookingRequestRepository.findByUserToId(getLogedUserId())) {
            
             System.out.println("STATE="+state);
             System.out.println("bookingState="+bookingRequest.getStateOfRequest());
 
 
             if(bookingRequest.getStateOfRequest().equals(state)){
                 needed.add(bookingRequest);
             }

            
 
        }

        for (AdvertisementCreationDTO advertisementCreationDTO : advertisements) {
            
            for (BookingRequest book : needed) {
                
                if(book.getAdvertisementId().equals(advertisementCreationDTO.getId())){

                    BookingRequestFrontDTO novi=new BookingRequestFrontDTO(book.getId(),book.getUserForId(),book.getUserToId(),
                    book.getGroupId(),book.getStateOfRequest(),advertisementCreationDTO,book.isTogether(),book.getTimeFrom(),
                    book.getTimeTo());

                    forFront.add(novi);
                }

            }

        }


         return forFront;
     }
 


    public List<Long> getSpecificGroupsForBuyer (RequestStates state){
        List<Long> group=new ArrayList<Long>();
        System.out.println(getLogedUserId());
        
        for (BookingRequest bookingRequest : bookingRequestRepository.findByUserToId(getLogedUserId())) {
            
            System.out.println("prolazi kroz for");

            if(!group.contains(bookingRequest.getGroupId())){
                System.out.println("Usao u if");
                if(bookingRequest.getStateOfRequest().equals(state)){
                    System.out.println("Usao drugi if");
                group.add(bookingRequest.getGroupId());
                }
            }
        }

        return group;


    }

    public List<BookingRequest> findAll()
    {
        return bookingRequestRepository.findAll();
    }

    public void acceptRequest(Long grupa){

        System.out.println("Broj zahteva"+bookingRequestRepository.findAllByGroupId(grupa).size());

        for (BookingRequest request : bookingRequestRepository.findAllByGroupId(grupa)) {
            
            
            request.setStateOfRequest(RequestStates.RESERVED);

            bookingRequestRepository.save(request);

        }
    }

    public Long getLogedUserId(){
    

        Long id = restTemplate.exchange("http://auth/getUserId", HttpMethod.GET, null, 
        new ParameterizedTypeReference<Long>() {} ).getBody();
    
        System.out.println("Nasao je id="+id);
    
        return id;
    }

    public void saveReserve(ReservationDTO reservationDTO){
        BookingRequest toBook = new BookingRequest(getLogedUserId(),reservationDTO.getAdvertisementId(), reservationDTO.getTimeFrom(), reservationDTO.getTimeTo(),RequestStates.PAID);



        List<BookingRequest>bookedTimes=bookingRequestRepository.findAll();
        
        LocalDateTime timeFrom = toBook.getTimeFrom();
        LocalDateTime timeTo = toBook.getTimeTo();
        
        for (BookingRequest booking : bookedTimes) {
            if (booking.getAdvertisementId() == toBook.getAdvertisementId()) {
        
                if (timeFrom.isAfter(booking.getTimeFrom()) && timeFrom.isBefore(booking.getTimeTo())) {
                    booking.setStateOfRequest(RequestStates.CANCELED);
                    bookingRequestRepository.save(booking);

                }
        
                if (timeTo.isAfter(booking.getTimeFrom()) && timeTo.isBefore(booking.getTimeTo())) {
                    booking.setStateOfRequest(RequestStates.CANCELED);
                    bookingRequestRepository.save(booking);
                }
            }
        }
        
        bookingRequestRepository.save(toBook);


    }

}