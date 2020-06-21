package com.projekat.XML.service;

import com.projekat.XML.dtos.AgentDTO;
import com.projekat.XML.dtos.CarDTO;
import com.projekat.XML.dtos.CarReportDTO;
import com.projekat.XML.dtos.UserDTO;
import com.projekat.XML.enums.UserType;
import com.projekat.XML.model.*;
import com.projekat.XML.repository.AdvertisementRepository;
import com.projekat.XML.repository.AgentRepository;
import com.projekat.XML.repository.CarReportRepository;
import com.projekat.XML.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private CarReportRepository carReportRepository;

    public int save(AgentDTO agentDTO) {
        EntityUser user;

        user = userRepository.findByLoginInfo_Username(agentDTO.getUsername());

        if(user != null){
            return 1;
        }

        user = userRepository.findByLoginInfo_Email(agentDTO.getEmail());

        if(user != null){
            return 2;
        }

        user = userRepository.findByJmbg(agentDTO.getJmbg());

        if(user != null){
            return 3;
        }

        LoginInfo loginInfo = new LoginInfo(agentDTO.getEmail(), agentDTO.getUsername(), agentDTO.getPassword(), false, false, false, true);

        Agent agent = new Agent(agentDTO.getName(), agentDTO.getSurname(), loginInfo, agentDTO.getJmbg(),
                agentDTO.getPhone(), UserType.AGENT, 0, true, agentDTO.getAdress(), agentDTO.getBsregnum());

        agentRepository.save(agent);

        return 0;
    }

    public boolean checkPasswordChanged(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        Long id = (Long) session.getAttribute("user");

        Agent opt = agentRepository.findOneById(id);
        if(opt!=null){
            
            return opt.isFirst_login() ? false : true;
        }

        return false;
    }

    public List<CarDTO> findOwnersCars(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        Long id = (Long) session.getAttribute("user");
        List<Advertisement> advertisements = advertisementRepository.findAllByPostedBy_Id(id);
        List<CarDTO> cars = new ArrayList<CarDTO>();
        for(int i = 0;i < advertisements.size(); i++){
            CarDTO car = new CarDTO();
            Advertisement ad = advertisements.get(i);

            car.setId(ad.getId());
            car.setBrand(ad.getBrand().getName());
            car.setCarClass(ad.getCarClass().getName());
            car.setCarSeats(ad.getCarSeats());
            car.setFuelType(ad.getFuelType().getName());
            car.setModel(ad.getModel().getName());
            car.setName(ad.getName());
            car.setTransmissionType(ad.getTransmissionType().getName());
            car.setTravelled(ad.getTravelled());

            cars.add(car);
        }

        return cars;
    }

    public void saveReport(CarReportDTO carReportDTO){
        Advertisement ad = advertisementRepository.findOneByid(carReportDTO.getCarId());

        ad.setTravelled(ad.getTravelled() + carReportDTO.getTravelled());

        CarReport newReport = new CarReport(carReportDTO.getTravelled(), carReportDTO.getComment(), ad);

        carReportRepository.save(newReport);
    }
}
