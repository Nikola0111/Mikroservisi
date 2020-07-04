package com.projekat.XML.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.projekat.XML.enums.UserType;
import com.projekat.XML.model.*;
import com.projekat.XML.repository.*;
import com.projekat.XML.service.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private EndUserService endUserService;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private LoginInfoService loginInfoService;

    @Autowired
    private EndUserRepository endUserRepository;


    public EntityUser findUserByEmailAndPassword(EntityUser user) {
        return userRepository.findByLoginInfo_EmailAndLoginInfo_Password(user.getLoginInfo().getEmail(),
                user.getLoginInfo().getPassword());
    }

    public void login(LoginInfo log) {

        // Sacuvati korisnika u sesiji
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes(); 
		HttpSession session = attr.getRequest().getSession(true); 

        for (EntityUser user : userRepository.findAll()) {

            if (user.getLoginInfo().getId().equals(log.getId())) {
                session.setAttribute("user", user.getId());
            }

        }
    }

    public Long getLoggedUserId() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        Long userId = (Long) session.getAttribute("user");
        return userId;

    }

    public EntityUser getLoggedUser() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        Long userId = (Long) session.getAttribute("user");

        EntityUser user = userRepository.findOneByid(userId);
        
        return user;
    }

    public EndUser getLoggedEndUser() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        List<EndUser> all = endUserRepository.findAll();
        Long userId = (Long) session.getAttribute("user");

        for (EndUser endUser : all) {
            if (endUser.getUser().getId().equals(userId)) {
                return endUser;
            }
        }
        return null;
    }

    public int getEndusersNumberOfAds(Long endUserId) {
        EndUser endUser = endUserRepository.findOneById(endUserId);
        if (endUser == null) {
            return 0;
        }
        return endUser.getNumberOfAds();
    }

    public List<EntityUser> getAll(){



        return userRepository.findAll();
    }

    public Long increaseEndUsersNumberOfAds() {
        EndUser endUser = getLoggedEndUser();
        endUser.setNumberOfAds(endUser.getNumberOfAds() + 1);
        endUserRepository.save(endUser);
        return endUser.getId();
    }

    public void saveInDatabase(EntityUser entity) {

        userRepository.save(entity);

    }

    public EntityUser findByUsername(String username) {
        return userRepository.findByLoginInfo_Username(username);
    }

    public void saveNewUser(EntityUser entityUser) {

     

       

        LoginInfo loginInfo = new LoginInfo(entityUser.getUsername(), entityUser.getPassword(),
                entityUser.getLoginInfo().getEmail(),
                // salt,
                // ApplicationUserRole.ENDUSER.getGrantedAuthorities(),
                true, true, true, true);

        loginInfoService.save(loginInfo);

        entityUser.setLoginInfo(loginInfoService.findOneById(loginInfo.getId()));

        // cuvanje u bazi
        saveInDatabase(entityUser);

        EndUser endUser = new EndUser();

        endUser.setNumber_of_requests(0);
        endUser.setAccount_activated(false);
        endUser.setAdminApproved(false);
        endUser.setUser(findOneByid(entityUser.getId()));
        endUser.setNumberOfAds(0);

        endUserService.save(endUser);

        // String verificationToken = UUID.randomUUID().toString();
        // verificationTokenService.save(endUser, verificationToken);
    }


   

    public void logOut() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.invalidate();
        System.out.println("Izlogovao se");
    }

    public List<EntityUser> findAll() {
        return userRepository.findAll();
    }

    public EntityUser findOneByid(Long id) {
        return userRepository.findOneByid(id);
    }

    
    public void changePassword(Long id, String password){
     /*   
        EntityUser user = userRepository.findById(id);
        System.out.println(user);
        if(user.getUserType() == UserType.AGENT){
            Agent agent = agentRepository.findById(id);

            agent.setFirst_login(false);
            agent.getUser().getLoginInfo().setPassword(password);

            agentRepository.save(agent);
        }/*else{
            user.setPassword(password);
            userRepository.save(user);
        }
        */
    }

    
}
