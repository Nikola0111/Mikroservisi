package com.Message.Message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.core.ParameterizedTypeReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

import javax.servlet.http.HttpSession;

import com.Message.Message.dtos.*;
import com.Message.Message.enums.RequestStates;
import com.Message.Message.model.*;
import com.Message.Message.repository.*;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoggerService loggerService;

    public List<MessageFrontDTO> getConversation(Long id, List<UserDTO> allUsers, Long loggedUserId) {
        List<Message> allMessages = messageRepository.findAll();
        List<Message> filteredMessages = new ArrayList<Message>();
        List<MessageFrontDTO> messageDTOs = new ArrayList<MessageFrontDTO>();

        for (Message message : allMessages) {
            if (message.getSenderId() == id && message.getReceiverId() == loggedUserId) {
                filteredMessages.add(message);
            }

            if (message.getSenderId() == loggedUserId && message.getReceiverId() == id) {
                filteredMessages.add(message);
            }
        }

        Collections.sort(filteredMessages, new Comparator<Message>() {
            public int compare(Message m1, Message m2) {
                return m1.getTimeSent().compareTo(m2.getTimeSent());
            }
        });

        for (Message message : filteredMessages) {
            messageDTOs.add(new MessageFrontDTO(message, allUsers));
        }
        return messageDTOs;
    }

    public int newMessage(MessageDTO messageDTO, List<UserDTO> allUsers, Long id) {

        System.out.println(messageDTO.email + " " + messageDTO.text);

        if (id == null) {
            return 0;
        }

        Message message = new Message();
        message.setSenderId(id);

        if (messageDTO.getEmail() != null && !messageDTO.getEmail().equals("")) {
            for (UserDTO user : allUsers) {
                if (user.getLoginInfo().getEmail().equals(messageDTO.getEmail())) {

                    message.setReceiverId(user.getId());
                }
            }
        } else {
            for (UserDTO user : allUsers) {
                if (user.getId().equals(messageDTO.getReceiverID())) {

                    message.setReceiverId(user.getId());
                }
            }
        }

        message.setText(messageDTO.text);
        message.setTimeSent(LocalDateTime.now());

        messageRepository.save(message);
        loggerService.doLog("12", "Tekst: " + message.getText() + " Primalac: " + message.getReceiverId(), "INFO");
        return 1;
    }

    public List<UserDTO> getInboxUsers(List<UserDTO> allUsers, Long id) {
        List<Message> allMessages = messageRepository.findAll();
        List<Message> filteredMessages = new ArrayList<Message>();
        List<UserDTO> users = new ArrayList<UserDTO>();

        for (Message message : allMessages) {
            if (message.getReceiverId() == id) {
                filteredMessages.add(message);
            }

            if (message.getSenderId() == id) {
                filteredMessages.add(message);
            }
        }

        System.out.println("FilterMessages suuuuuuuuu prvi for=" + filteredMessages.size());

        // System.out.println("USER======"+users.get(0).getId()+"EMAIL"+users.get(0).getLoginInfo().getEmail());

        for (Message message : filteredMessages) {
            if (!users.contains(getUserDTOById(allUsers, message.getReceiverId())) && message.getReceiverId() != id) {
                users.add(getUserDTOById(allUsers, message.getReceiverId()));
            }

            if (!users.contains(getUserDTOById(allUsers, message.getSenderId())) && message.getSenderId() != id) {
                users.add(getUserDTOById(allUsers, message.getSenderId()));
            }
        }

        System.out.println("Posle drugog fora===" + users.size());

        // return users; OVAKO KASNIJE
        return users;
    }

    public List<UserDTO> getAllMessagableUsers(List<BookingRequestDTO> bookingRequests, List<UserDTO> allUsers,
            Long id) {
        List<UserDTO> messagableUsers = new ArrayList<UserDTO>();

        for (BookingRequestDTO bookingRequest : bookingRequests) {
            if (bookingRequest.getUserForId().equals(id)
                    && (bookingRequest.getStateOfRequest() == RequestStates.RESERVED
                            || bookingRequest.getStateOfRequest() == RequestStates.PAID)) {
                if (!messagableUsers.contains(getUserDTOById(allUsers, bookingRequest.getUserToId()))) {
                    messagableUsers.add(getUserDTOById(allUsers, bookingRequest.getUserToId()));
                }
            }

            if (bookingRequest.getUserToId().equals(id) && (bookingRequest.getStateOfRequest() == RequestStates.RESERVED
                    || bookingRequest.getStateOfRequest() == RequestStates.PAID)) {
                if (!messagableUsers.contains(getUserDTOById(allUsers, bookingRequest.getUserForId()))) {
                    messagableUsers.add(getUserDTOById(allUsers, bookingRequest.getUserForId()));
                }
            }
        }

        return messagableUsers;
    }

    public UserDTO getUserDTOById(List<UserDTO> allUsers, Long userId) {
        UserDTO user = new UserDTO();
        for (UserDTO userDTO : allUsers) {
            if (userDTO.getId().equals(userId)) {
                user = userDTO;
                break;
            }
        }
        return user;
    }

    // private Long getLogedUserId(Long id) {
    // Long id = restTemplate
    // .exchange("http://auth/getUserId", HttpMethod.GET, null, new
    // ParameterizedTypeReference<Long>() {
    // }).getBody();

    // System.out.println("Nasao je id=" + id);

    // return id;
    // }

}