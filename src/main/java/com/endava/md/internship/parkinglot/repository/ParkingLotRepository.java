package com.endava.md.internship.parkinglot.repository;

import com.endava.md.internship.parkinglot.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

    Boolean existsByAddress(String address);

    Boolean existsByName(String name);
}
