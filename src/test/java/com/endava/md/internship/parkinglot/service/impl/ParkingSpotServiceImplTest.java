package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.ParkingSpotResponseDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotTypeDto;
import com.endava.md.internship.parkinglot.exception.OccupiedParkingSpotException;
import com.endava.md.internship.parkinglot.exception.ParkingSpotNotFoundException;
import com.endava.md.internship.parkinglot.model.ParkingSpot;
import com.endava.md.internship.parkinglot.model.ParkingSpotType;
import com.endava.md.internship.parkinglot.repository.ParkingSpotRepository;
import com.endava.md.internship.parkinglot.utils.ParkingSpotResponseDTOUtils;
import com.endava.md.internship.parkinglot.utils.ParkingSpotUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ParkingSpotServiceImplTest {

    private Long parkingSpotId;

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @InjectMocks
    private ParkingSpotServiceImpl parkingSpotServiceImpl;

    @BeforeEach
    void setUp() {
        parkingSpotId = 10L;
    }

    @Test
    void should_ReturnChangedParkingSpot_When_NewParkingSpotTypeWasPassed() {
        ParkingSpotTypeDto parkingSpotTypeDto = ParkingSpotTypeDto
                .builder()
                .parkingSpotType(ParkingSpotType.HANDICAP.toString())
                .build();

        ParkingSpotResponseDto expectedParkingSpotResponseDto = ParkingSpotResponseDTOUtils.getPreparedParkingSpotResponseDto();
        ParkingSpot parkingSpot = ParkingSpotUtils.getPreparedParkingSpot();

        when(parkingSpotRepository.findBySpotId(parkingSpotId)).thenReturn(Optional.of(parkingSpot));

        ParkingSpotResponseDto parkingSpotResponseDto = parkingSpotServiceImpl.changeParkingSpotType(parkingSpotId, parkingSpotTypeDto);

        assertThat(parkingSpotResponseDto).isEqualTo(expectedParkingSpotResponseDto);
    }

    @Test
    void should_ReturnThisSameParkingSpot_When_WasPassedAlreadyEstablishedParkingSpotType() {
        ParkingSpotTypeDto parkingSpotTypeDto = ParkingSpotTypeDto
                .builder()
                .parkingSpotType(ParkingSpotType.REGULAR.toString())
                .build();

        ParkingSpotResponseDto expectedParkingSpotResponseDto =
                ParkingSpotResponseDTOUtils.getPreparedParkingSpotResponseDtoWithRegularType();
        ParkingSpot parkingSpot = ParkingSpotUtils.getPreparedParkingSpot();

        when(parkingSpotRepository.findBySpotId(parkingSpotId)).thenReturn(Optional.of(parkingSpot));

        ParkingSpotResponseDto parkingSpotResponseDto = parkingSpotServiceImpl.changeParkingSpotType(parkingSpotId, parkingSpotTypeDto);

        assertThat(parkingSpotResponseDto).isEqualTo(expectedParkingSpotResponseDto);
    }

    @Test
    void should_ThrowOccupiedParkingSpotException_When_PassedParkingSpotOccupiedFieldHasValueTrue() {
        ParkingSpotTypeDto parkingSpotTypeDto = ParkingSpotTypeDto
                .builder()
                .parkingSpotType(ParkingSpotType.FAMILY_FRIENDLY.toString())
                .build();
        ParkingSpot parkingSpot = ParkingSpotUtils.getPreparedParkingSpotWithValueTrueForOccupiedField();

        when(parkingSpotRepository.findBySpotId(parkingSpotId)).thenReturn(Optional.of(parkingSpot));

        assertThatThrownBy(() -> parkingSpotServiceImpl.changeParkingSpotType(parkingSpotId, parkingSpotTypeDto))
                .hasMessage("Parking Spot is Occupied!")
                .isInstanceOf(OccupiedParkingSpotException.class);
    }

    @Test
    void should_ThrowParkingSpotNotFoundException_When_ParkingSpotWithPassedIdDoesNotExist() {
        parkingSpotId = 333L;
        ParkingSpotTypeDto parkingSpotTypeDto = ParkingSpotTypeDto
                .builder()
                .parkingSpotType(ParkingSpotType.FAMILY_FRIENDLY.toString())
                .build();

        when(parkingSpotRepository.findBySpotId(parkingSpotId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> parkingSpotServiceImpl.changeParkingSpotType(parkingSpotId, parkingSpotTypeDto))
                .hasMessage("Parking Spot is Not Found!")
                .isInstanceOf(ParkingSpotNotFoundException.class);
    }

}