package com.endava.md.internship.parkinglot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "parking_spot_details")
public class ParkingView {
    @Id
    @Column(name = "spot_id")
    private Long spotId;

    @Column(name = "spot_name")
    private String spotName;

    @Column(name = "occupied")
    private boolean occupied;

    @Enumerated(EnumType.STRING)
    @Column(name = "spot_type")
    private ParkingSpotType spotType;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "level_id")
    private Long levelId;

    @Column(name = "level_name")
    private String levelName;

    @Column(name = "parking_lot_id")
    private Long parkingLotId;

    @Column(name = "parking_name")
    private String parkingLotName;
}
