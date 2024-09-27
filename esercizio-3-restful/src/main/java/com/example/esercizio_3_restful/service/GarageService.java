package com.example.esercizio_3_restful.service;

import com.example.esercizio_3_restful.Entity.Garage;
import com.example.esercizio_3_restful.Entity.User;
import com.example.esercizio_3_restful.dto.GarageDTO;
import com.example.esercizio_3_restful.exception.GarageNotFoundException;
import com.example.esercizio_3_restful.exception.UserNotFoundException;
import com.example.esercizio_3_restful.repository.GarageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageService {
    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private UserService userService;

    private static final Logger loggerInfo = LoggerFactory.getLogger("loggerInfo");

    public Garage createGarage(GarageDTO garageDTO) throws UserNotFoundException {
        Garage garage = new Garage();
        garage.setLevels(garageDTO.getLevels());

        garageRepository.save(garage);

        loggerInfo.info("Garage with id " + garage.getId() + " created.");
        return garage;
    }

    public Garage getGarageById(Long id) throws GarageNotFoundException {
        return garageRepository.findById(id)
                .orElseThrow(() -> new GarageNotFoundException("Garage not found with id: " + id));
    }

    public List<Garage> getAllGarages() {
        List<Garage> garages = garageRepository.findAll();
        loggerInfo.info("Retrieved all dining tables.");
        return garages;
    }

    public Page<Garage> getAllGarages(int page, String sortBy) {
        int fixedSize = 15;
        Pageable pageable = PageRequest.of(page, fixedSize, Sort.by(sortBy));
        Page<Garage> garages = garageRepository.findAll(pageable);
        loggerInfo.info("Retrieved dining tables page " + page + " with fixed size " + fixedSize + " sorted by " + sortBy);
        return garages;
    }

    public Garage updateGarage(Long id, GarageDTO garageDTO) throws GarageNotFoundException, UserNotFoundException {
        Garage garage = getGarageById(id);

        garage.setLevels(garageDTO.getLevels());

        garageRepository.save(garage);
        loggerInfo.info("Garage with id " + id + " updated.");

        return garage;
    }

    public String deleteGarage(Long id) throws GarageNotFoundException {
        Garage garage = getGarageById(id);
        garageRepository.delete(garage);
        loggerInfo.info("Garage with id " + id + " deleted successfully.");
        return "Garage with id " + id + " deleted successfully.";
    }
}

