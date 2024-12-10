package com.endava.md.internship.parkinglot.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Entity
@Table(name = "parking_lot_users")
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotUser {
    @EmbeddedId
    private ParkingLotUserId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", referencedColumnName = "parking_lot_id", insertable = false, updatable = false)
    private ParkingLot parkingLot;
}