package com.example.shift_scheduler.service;

import com.example.shift_scheduler.entity.ShiftRequest;
import com.example.shift_scheduler.entity.User;
import com.example.shift_scheduler.repository.ShiftRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShiftRequestService {

    @Autowired
    private ShiftRequestRepository shiftRequestRepository;

    public List<ShiftRequest> getAllShiftRequests() {
        return shiftRequestRepository.findAll();
    }

    public void saveShiftRequest(ShiftRequest shiftRequest) {
        shiftRequestRepository.save(shiftRequest);
    }

    public List<ShiftRequest> getAllShiftRequestsByUser(User user) {
        return shiftRequestRepository.findByUser(user);
    }

    public ShiftRequest getShiftRequestByUserAndDate(User user, LocalDate date) {
        return shiftRequestRepository.findByUserAndDate(user, date);
    }

    public void deleteShiftRequest(ShiftRequest shiftRequest) {
        shiftRequestRepository.delete(shiftRequest);
    }

    public boolean isShiftRequestExist(User user, LocalDate date) {
        return shiftRequestRepository.findByUserAndDate(user, date) != null;
    }

    public void deleteAllShiftRequests() {
        shiftRequestRepository.deleteAll();
    }

}
