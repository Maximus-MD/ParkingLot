package com.endava.md.internship.parkinglot.repository;

import com.endava.md.internship.parkinglot.model.ParkingLevel;
import com.endava.md.internship.parkinglot.model.ParkingSpot;
import com.endava.md.internship.parkinglot.model.ParkingSpotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Integer> {

    @Query("SELECT ps FROM ParkingSpot ps " +
            "JOIN ps.parkingLevel pl " +
            "JOIN pl.parkingLot lot " +
            "WHERE ps.name = :name AND lot.name = :parkingName")
    Optional<ParkingSpot> findByNameAndParkingName(String name, String parkingName);

    Optional<ParkingSpot> findByType(ParkingSpotType type);

    Optional<ParkingSpot> findByOccupied(boolean occupied);
    Optional<ParkingSpot> findBySpotId(Long id);
    int countByParkingLevelAndOccupied(ParkingLevel parkingLevel, boolean occupied);
}
