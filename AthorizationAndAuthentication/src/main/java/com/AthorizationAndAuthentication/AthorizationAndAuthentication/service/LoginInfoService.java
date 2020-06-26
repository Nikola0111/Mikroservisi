package com.AthorizationAndAuthentication.AthorizationAndAuthentication.service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.EndUser;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.EntityUser;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.LoginInfo;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.repository.EndUserRepository;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.repository.LoginInfoRepository;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginInfoService implements UserDetailsService {


   @Autowired
   private LoginInfoRepository loginInfoRepository;

   @Autowired
   private EndUserRepository endUserRepository;

   @Autowired 
   private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        LoginInfo login = loginInfoRepository.findByUsername(username);
        EndUser endUser = new EndUser();
        for(EntityUser user : userRepository.findAll()){
            if(user.getLoginInfo().getId().equals(login.getId())){
                endUser = getEndUserByUser(user);

              
                if(endUser != null ){
                    if(endUser.isAccount_activated()) {
                        return login;
                    }
                } else {
                    return login;
                }
            }
        }

        return null;

    }

    public EndUser getEndUserByUser(EntityUser user){
        for(EndUser endUser : endUserRepository.findAll()){
            if(endUser.getUser().getId().equals(user.getId())){
                return endUser;
            }
        }
        return null;
    }


    public void save(LoginInfo loginInfo){

        loginInfoRepository.save(loginInfo);

    }

    public String findSaltByUsername(String username){

return loginInfoRepository.findByUsername(username).getSalt();

    }


    public LoginInfo findByEmail(String email){


        return loginInfoRepository.findByEmail(email);
    }


    public LoginInfo findOneById(Long id){

        return loginInfoRepository.findOneById(id);

    }


    public LoginInfo findOneByUsername(String username){

        return loginInfoRepository.findByUsername(username);
    }

}