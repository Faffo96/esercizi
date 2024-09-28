package com.example.esercizio_3_restful.service;

import com.example.esercizio_3_restful.Entity.Garage;
import com.example.esercizio_3_restful.Entity.Slot;
import com.example.esercizio_3_restful.Entity.User;
import com.example.esercizio_3_restful.Enum.SlotType;
import com.example.esercizio_3_restful.dto.SlotDTO;
import com.example.esercizio_3_restful.exception.*;
import com.example.esercizio_3_restful.repository.SlotRepository;
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
public class SlotService {
    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private GarageService garageService;

    private static final Logger loggerInfo = LoggerFactory.getLogger("loggerInfo");

    public Slot createSlot(SlotDTO slotDTO) throws UserNotFoundException, GarageNotFoundException {
        Slot slot = new Slot();
        Garage garage = garageService.getGarageById(slotDTO.getGarage().getId());
        slot.setGarage(slotDTO.getGarage());
        slot.setFull(slotDTO.isFull());
        slot.setType(slotDTO.getType());
        slot.setLevel(slotDTO.getLevel());

        slotRepository.save(slot);

        loggerInfo.info("Slot with id " + slot.getId() + " created.");
        return slot;
    }

    public Slot getSlotById(Long id) throws SlotNotFoundException {
        return slotRepository.findById(id)
                .orElseThrow(() -> new SlotNotFoundException("Slot not found with id: " + id));
    }

    public List<Slot> getAllSlots() {
        List<Slot> slots = slotRepository.findAll();
        loggerInfo.info("Retrieved all dining tables.");
        return slots;
    }

    public Page<Slot> getAllSlots(int page, String sortBy) {
        int fixedSize = 15;
        Pageable pageable = PageRequest.of(page, fixedSize, Sort.by(sortBy));
        Page<Slot> slots = slotRepository.findAll(pageable);
        loggerInfo.info("Retrieved dining tables page " + page + " with fixed size " + fixedSize + " sorted by " + sortBy);
        return slots;
    }

    public Slot updateSlot(Long id, SlotDTO slotDTO) throws SlotNotFoundException, UserNotFoundException, GarageNotFoundException {
        Slot slot = getSlotById(id);

        Garage garage = garageService.getGarageById(slotDTO.getGarage().getId());
        slot.setGarage(slotDTO.getGarage());
        slot.setFull(slotDTO.isFull());
        slot.setType(slotDTO.getType());
        slot.setLevel(slotDTO.getLevel());

        slotRepository.save(slot);
        loggerInfo.info("Slot with id " + id + " updated.");

        return slot;
    }

    public String deleteSlot(Long id) throws SlotNotFoundException {
        Slot slot = getSlotById(id);
        slotRepository.delete(slot);
        loggerInfo.info("Slot with id " + id + " deleted successfully.");
        return "Slot with id " + id + " deleted successfully.";
    }

    public List<Slot> getSlotByGarageAndLevel(Garage garage, int level) throws SlotNotFoundException {
        try {
            return slotRepository.findByGarageAndLevel(garage, level);
        } catch (IllegalArgumentException e) {
            throw new SlotNotFoundException("Invalid garage level: " + level);
        }
    }

    public List<Slot> getSlotsByGarageAndType(Garage garage, SlotType slotType) throws InvalidSlotTypeException {
        try {
            return slotRepository.findByGarageAndType(garage, slotType);
        } catch (IllegalArgumentException e) {
            throw new InvalidSlotTypeException("Invalid slot type: " + slotType);
        }
    }

    public void checkCategoryHalfFull(Slot slot) throws InvalidSlotTypeException, MoreThanHalfSlotsException {
        List<Slot> slots = getSlotsByGarageAndType(slot.getGarage(), slot.getType());
        List<Slot> fullSlots = slots.stream().filter(Slot::isFull).toList();
        if (fullSlots.size() > (slots.size()/2)) {
            throw new MoreThanHalfSlotsException("Impossibile prenotare. Il numero di auto " + slot.getType() + " supera il 50% della capienza per la sua categoria.");
        }
    }
}

