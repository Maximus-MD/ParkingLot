package com.endava.md.internship.parkinglot.repository;

import com.endava.md.internship.parkinglot.model.ParkingSpot;
import com.endava.md.internship.parkinglot.model.ParkingSpotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Integer> {
    Optional<ParkingSpot> findByType(ParkingSpotType type);
    Optional<ParkingSpot> findByOccupied(boolean occupied);
    Optional<ParkingSpot> findBySpotId(Long id);
}
