package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotTypeDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotResponseDto;
import com.endava.md.internship.parkinglot.dto.ResponseDTO;
import com.endava.md.internship.parkinglot.dto.ResponseGenericErrorDTO;
import com.endava.md.internship.parkinglot.service.ParkingSpotService;
import com.endava.md.internship.parkinglot.utils.swagger.PreparedParkingSpotDtoUtils;
import com.endava.md.internship.parkinglot.utils.swagger.PreparedResponseDtoUtils;
import com.endava.md.internship.parkinglot.utils.swagger.PreparedServerErrorUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking-spots")
@RequiredArgsConstructor
@Tag(name = "Parking Spot Controller",
        description = "Handles parking spot operations. Contains change parking spot type endpoint and endpoint for get all available parking spot types")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @Operation(
            summary = "Reserve parking spot",
            description = "This endpoint is responsible for spot reserving",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ParkingSpotDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Successfully reserved parking spot",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ParkingSpotResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedParkingSpotDtoUtils.PREPARED_SUCCESSFULLY_RESERVED_PARKING_SPOT_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "Failed parking spot reserving",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ParkingSpotResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedParkingSpotDtoUtils.PREPARED_FAILED_RESERVED_PARKING_SPOT_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "User authentication failed",
                            responseCode = "401",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_FAILED_AUTHENTICATION_RESPONSE_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "Internal server error during reserving parking spot",
                            responseCode = "500",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseGenericErrorDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedServerErrorUtils.PREPARED_SERVER_ERROR_RESPONSE)
                            )
                    )
            }
    )
    @PatchMapping("/reserve-spot")
    public ResponseEntity<ParkingLotResponseDto> reserveParkingSpot(@Valid @RequestBody ParkingSpotDto parkingSpotDto) {
        ParkingLotResponseDto response = parkingSpotService.occupyParkingSpot(parkingSpotDto);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Change parking spot type",
            description = "This endpoint is responsible for changing the Parking Spot Type",
            parameters = {@Parameter(name = "id", description = "Parking Spot Id", example = "2771")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ParkingSpotTypeDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Successfully changed parking spot type",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ParkingSpotResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedParkingSpotDtoUtils.PREPARED_PARKING_SPOT_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "Nonexistent parking spot id",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_NONEXISTENT_PARKING_SPOT_ID_RESPONSE_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "User authentication failed",
                            responseCode = "401",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_FAILED_AUTHENTICATION_RESPONSE_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "Access to requested endpoint with not admin role is forbidden",
                            responseCode = "403",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_FAILED_AUTHENTICATION_RESPONSE_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "Internal server error during change parking spot type process",
                            responseCode = "500",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseGenericErrorDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedServerErrorUtils.PREPARED_SERVER_ERROR_RESPONSE)
                            )
                    )
            }
    )
    @PatchMapping("/change-type/{id}")
    public ResponseEntity<ParkingSpotResponseDto> changeParkingSpotType(
            @PathVariable("id") Long id,
            @Valid @RequestBody ParkingSpotTypeDto parkingSpotTypeDto
    ) {
        ParkingSpotResponseDto parkingSpotResponseDto =
                parkingSpotService.changeParkingSpotType(id, parkingSpotTypeDto);

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotResponseDto);
    }

    @Operation(
            summary = "Get all parking spot types",
            description = "This endpoint is responsible for get all parking spot types",
            responses = {
                    @ApiResponse(
                            description = "Successful get all parking spot types request",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = List.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedParkingSpotDtoUtils.PREPARED_PARKING_SPOT_TYPES)
                            )
                    ),
                    @ApiResponse(
                            description = "Invalid data during parking lot creation",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_FAILED_AUTHENTICATION_RESPONSE_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "User authentication failed",
                            responseCode = "401",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_FAILED_AUTHENTICATION_RESPONSE_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "Internal server error during get all parking lots",
                            responseCode = "500",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseGenericErrorDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedServerErrorUtils.PREPARED_SERVER_ERROR_RESPONSE)
                            )
                    )
            }
    )
    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllParkingSpotTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.getAllParkingSpotTypes());
    }

}
