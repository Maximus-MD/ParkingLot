package com.endava.md.internship.parkinglot.repository;

import com.endava.md.internship.parkinglot.model.ParkingLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingLevelRepository extends JpaRepository<ParkingLevel, Integer> {
    Optional<ParkingLevel> findByLevelName(String levelName);
}
