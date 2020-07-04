package com.projekat.XML.service;

import com.projekat.XML.dtos.CarDetailsDTO;
import com.projekat.XML.enums.Data;
import com.projekat.XML.model.CarDetails;
import com.projekat.XML.repository.CarDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CarDetailsService {

    @Autowired
    private CarDetailsRepository carDetailsRepository;

    public List<CarDetails> getAll(){
        return carDetailsRepository.findAll();
    }

    public int save(CarDetailsDTO carDetailsDTO){
        CarDetails temp;

        temp = carDetailsRepository.findByCode(carDetailsDTO.getCode());

        if(temp != null){
            System.out.println("Nasao po kodu");
            return 2;
        }

        temp = carDetailsRepository.findByName(carDetailsDTO.getName());

        if(temp != null){
            return 1;
        }
        Data type = Data.CarClass;
        System.out.println(carDetailsDTO.getType());
        if(carDetailsDTO.getType().equals("CarClass")){
            type = Data.CarClass;
        } else if(carDetailsDTO.getType().equals("CarModel")){

            type = Data.CarModel;
        } else if(carDetailsDTO.getType().equals("Fuel")){
            type = Data.Fuel;
        } else if(carDetailsDTO.getType().equals("Brand")){
            type = Data.Brand;
        } else if(carDetailsDTO.getType().equals("Gearshift")){
            type = Data.Gearshift;
        }

        carDetailsRepository.save(new CarDetails(carDetailsDTO.getCode(), carDetailsDTO.getName(), type));

        return 0;
    }

    @Transactional
    public void delete(String code){
        carDetailsRepository.deleteByCode(code);
    }
}
