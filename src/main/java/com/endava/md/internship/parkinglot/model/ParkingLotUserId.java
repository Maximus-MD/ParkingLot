package com.endava.md.internship.parkinglot.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotUserId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "parking_lot_id")
    private Long parkingLotId;
}