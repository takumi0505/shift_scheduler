package com.example.shift_scheduler.repository;

import com.example.shift_scheduler.entity.ShiftRequest;
import com.example.shift_scheduler.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftRequestRepository extends JpaRepository<ShiftRequest, Long> {
    List<ShiftRequest> findByUser(User user);
    ShiftRequest findByUserAndDate(User user, LocalDate date);
}
