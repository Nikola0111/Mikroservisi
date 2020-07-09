package com.AthorizationAndAuthentication.AthorizationAndAuthentication.service;

import com.AthorizationAndAuthentication.AthorizationAndAuthentication.dtos.AgentDTO;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.enums.UserType;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.Agent;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.EntityUser;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.LoginInfo;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.repository.AgentRepository;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.repository.LoginInfoRepository;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    @Autowired
    private UserRepository userRepository;

    public Long getAgentIDByEmail(String email){
        Agent agent = agentRepository.findByUser_LoginInfo_Email(email);

        return agent.getId();
    }

    // public boolean checkPasswordChanged(){
    //     ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
    //             .currentRequestAttributes();
    //     HttpSession session = attr.getRequest().getSession(true);
    //     Long id = (Long) session.getAttribute("user");

    //     Optional opt = agentRepository.findById(id);
    //     if(opt.isPresent()){
    //         Agent agent = (Agent) opt.get();
    //         return agent.isFirst_login() ? false : true;
    //     }

    //     return false;
    // }
    
    public Long getAgentIDByUserID(Long id){
        Agent agent = agentRepository.findByUser_Id(id);

        return agent.getId();
    }

    public String getAgentMail(Long id){
        Agent agent = agentRepository.findOneById(id);

        return agent.getUser().getLoginInfo().getEmail();
    }

    public Integer registerAgent(AgentDTO agentDTO){

        if(loginInfoRepository.findByUsername(agentDTO.getUsername()) != null) {
            return 1;
        }

        if(loginInfoRepository.findByEmail(agentDTO.getEmail()) != null) {
            return 2;
        }

        if(userRepository.findByJmbg(agentDTO.getJmbg()) != null) {
            return 3;
        }

        LoginInfo newLoginInfo = new LoginInfo(agentDTO.getUsername(), agentDTO.getPassword(), agentDTO.getEmail(), false,false,false,false);

        loginInfoRepository.save(newLoginInfo);

        EntityUser newUser = new EntityUser();
        newUser.setJmbg(agentDTO.getJmbg());
        newUser.setName(agentDTO.getName());
        newUser.setSurname(agentDTO.getSurname());
        newUser.setPhoneNumber(agentDTO.getPhone());
        newUser.setUserType(UserType.AGENT);
        newUser.setLoginInfo(newLoginInfo);

        userRepository.save(newUser);

        Agent newAgent = new Agent();
        newAgent.setAdress(agentDTO.getAdress());
        newAgent.setBsregnum(agentDTO.getBsregnum());
        newAgent.setUser(newUser);

        agentRepository.save(newAgent);

        return 0;
        
    }
}