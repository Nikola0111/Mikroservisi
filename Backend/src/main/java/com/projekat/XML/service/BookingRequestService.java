package com.projekat.XML.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.projekat.XML.dtos.ItemInCartDTO;
import com.projekat.XML.dtos.ReservationDTO;
import com.projekat.XML.enums.RequestStates;
import com.projekat.XML.model.Advertisement;
import com.projekat.XML.model.requests.BookingRequest;
import com.projekat.XML.repository.AdvertisementRepository;
import com.projekat.XML.repository.BookingRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class BookingRequestService {

    @Autowired
    BookingRequestRepository bookingRequestRepository;

    @Autowired
    AdvertisementRepository advertisementRepository;




    public void makeRequests(List<ItemInCartDTO> listaZahteva){
        //pretrazujem za svakog vlasnika, ako se pogodi da je isti, pogledam dal ide oglas odvojeno ili zajedno i na osnovu toga 
        //ubacujem u listu, cuvam i obrisem, onda se predje na sledeceg vlasnika
        //EROR STA AKO JE U PRVOJ LISTI OPET ISTI
        List<Long> vlasnici=new ArrayList<Long>();
        List<ItemInCartDTO> copyList=listaZahteva;
        ArrayList<ItemInCartDTO> together=new ArrayList<>();
        ArrayList<ItemInCartDTO> seprate=new ArrayList<>();


        for (ItemInCartDTO ItemInCartDTO : listaZahteva) {
            
            if(!vlasnici.contains(ItemInCartDTO.getAdvertisement().getPostedBy().getId())){

                vlasnici.add(ItemInCartDTO.getAdvertisement().getPostedBy().getId());

            }
        }

        System.out.println("Broj vlasnika je : "+vlasnici.size());


        for (Long id: vlasnici) {
            
            for (ItemInCartDTO adv :copyList) {
                
                if(adv.getAdvertisement().getPostedBy().getId().equals(id)){

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
                bookingRequestRepository.save(new BookingRequest( ItemInCartDTO2.getAdvertisement().getPostedBy().getId(),getLogedUserId(),
                 lastGroupId, RequestStates.PENDING,ItemInCartDTO2.getAdvertisement() , ItemInCartDTO2.isTogether(),ItemInCartDTO2.getTimeFrom(),ItemInCartDTO2.getTimeTo()));



                 
                }
                seprate.clear();

               

            }


           lastGroupId++;

            if(!together.isEmpty()){

                for (ItemInCartDTO ItemInCartDTO : together) {
                    
                bookingRequestRepository.save(new BookingRequest( ItemInCartDTO.getAdvertisement().getPostedBy().getId(),getLogedUserId(),
                lastGroupId, RequestStates.PENDING,ItemInCartDTO.getAdvertisement(),
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

     public List<Long> getSpecificGroupsForAgent(RequestStates state) {
         List<Long> group = new ArrayList<Long>();
         List<Long> groupsToRemove = new ArrayList<Long>();

         for (BookingRequest bookingRequest : bookingRequestRepository.findByUserForId(getLogedUserId())) {
             if (bookingRequest.getStateOfRequest().equals(state)
                     && !checkIfBookingRequestHasReservedTwin(bookingRequest)) {
                 if (!group.contains(bookingRequest.getGroupId())) {
                     group.add(bookingRequest.getGroupId());
                 }
             } else if (bookingRequest.getStateOfRequest().equals(state)
                     && checkIfBookingRequestHasReservedTwin(bookingRequest)) {
                 groupsToRemove.add(bookingRequest.getGroupId());
             }
         }

         for (Long groupToRemove : groupsToRemove) {
             if (group.contains(groupToRemove)) {
                 group.remove(groupToRemove);
             }
         }

         return group;

     }

     public boolean checkIfBookingRequestHasReservedTwin(BookingRequest bookingRequest) {
         List<BookingRequest> all = bookingRequestRepository.findByUserForId(getLogedUserId());

         for (BookingRequest booking : all) {
             if ((booking.getStateOfRequest() == RequestStates.RESERVED)
                     && (bookingRequest.getStateOfRequest() == RequestStates.PENDING)
                     && booking.getAdvertisement().getId().equals(bookingRequest.getAdvertisement().getId())
                     && ((booking.getTimeFrom().isAfter(bookingRequest.getTimeFrom())
                             && booking.getTimeFrom().isBefore(bookingRequest.getTimeTo()))
                             || (booking.getTimeTo().isAfter(bookingRequest.getTimeFrom())
                                     && booking.getTimeTo().isBefore(bookingRequest.getTimeTo()))
                             || (bookingRequest.getTimeFrom().isAfter(booking.getTimeFrom())
                                     && bookingRequest.getTimeFrom().isBefore(booking.getTimeTo()))
                             || (bookingRequest.getTimeTo().isAfter(booking.getTimeFrom())
                                     && bookingRequest.getTimeTo().isBefore(booking.getTimeTo()))
                             || (booking.getTimeFrom().equals(bookingRequest.getTimeFrom())
                                     || booking.getTimeFrom().equals(bookingRequest.getTimeTo())
                                     || booking.getTimeTo().equals(bookingRequest.getTimeFrom())
                                     || booking.getTimeTo().equals(bookingRequest.getTimeTo()))
                                     && booking.getTimeTo().equals(bookingRequest.getTimeTo()))) {
                 return true;
             }
         }

         return false;
     }

     public void saveReserve(ReservationDTO reservationDTO) {

         Advertisement ad = advertisementRepository.findOneByid(reservationDTO.getAdvertisementId());
         BookingRequest toBook = new BookingRequest(getLogedUserId(), ad,
                 reservationDTO.getTimeFrom(), reservationDTO.getTimeTo(), RequestStates.PAID);

         List<BookingRequest> bookedTimes = bookingRequestRepository.findAll();

         LocalDateTime timeFrom = toBook.getTimeFrom();
         LocalDateTime timeTo = toBook.getTimeTo();

         for (BookingRequest booking : bookedTimes) {
             if (booking.getAdvertisement().getId() == ad.getId() && !booking.getGroupId().equals(null)) {

                 if (timeFrom.isAfter(booking.getTimeFrom()) && timeFrom.isBefore(booking.getTimeTo())) {
                     cancelInSameGroup(bookedTimes, booking.getGroupId());
                 }

                 if (timeTo.isAfter(booking.getTimeFrom()) && timeTo.isBefore(booking.getTimeTo())) {
                     cancelInSameGroup(bookedTimes, booking.getGroupId());
                 }

                 if (booking.getTimeFrom().isAfter(timeFrom) && booking.getTimeFrom().isBefore(timeTo)) {
                     cancelInSameGroup(bookedTimes, booking.getGroupId());
                 }

                 if (booking.getTimeTo().isAfter(timeFrom) && booking.getTimeTo().isBefore(timeTo)) {
                     cancelInSameGroup(bookedTimes, booking.getGroupId());
                 }

                 if (timeFrom.equals(booking.getTimeFrom()) || timeFrom.equals(booking.getTimeTo())) {
                     cancelInSameGroup(bookedTimes, booking.getGroupId());
                 }

                 if (timeTo.equals(booking.getTimeFrom()) && timeTo.equals(booking.getTimeTo())) {
                     cancelInSameGroup(bookedTimes, booking.getGroupId());
                 }

             }
         }

         bookingRequestRepository.save(toBook);

     }
     
    public List<BookingRequest> getAllSpecificForBuyer(RequestStates state){
        List<BookingRequest> needed=new ArrayList<BookingRequest>();
 
 
        for (BookingRequest bookingRequest : bookingRequestRepository.findByUserToId(getLogedUserId())) {
            
             System.out.println("STATE="+state);
             System.out.println("bookingState="+bookingRequest.getStateOfRequest());
 
 
             if(bookingRequest.getStateOfRequest().equals(state)){
                 needed.add(bookingRequest);
             }
 
        }
         return needed;
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

    private Long getLogedUserId(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
    
        Long id = (Long) session.getAttribute("user");
        return id;
    }

    private void cancelInSameGroup(List<BookingRequest> svi, Long groupId) {

        for (BookingRequest booked : svi) {

            if (booked.getGroupId().equals(groupId)) {
                booked.setStateOfRequest(RequestStates.CANCELED);
                bookingRequestRepository.save(booked);

            }
        }

    }
    

}