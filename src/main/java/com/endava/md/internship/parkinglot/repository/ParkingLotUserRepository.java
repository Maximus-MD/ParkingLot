package com.endava.md.internship.parkinglot.repository;

import com.endava.md.internship.parkinglot.model.ParkingLotUser;
import com.endava.md.internship.parkinglot.model.ParkingLotUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotUserRepository extends JpaRepository<ParkingLotUser, ParkingLotUserId> {
    boolean existsById_UserId(Long userId);
}
