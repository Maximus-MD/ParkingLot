package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ParkingLotGeneralDetailsDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.dto.ResponseDTO;
import com.endava.md.internship.parkinglot.dto.ResponseGenericErrorDTO;
import com.endava.md.internship.parkinglot.service.ParkingLotService;
import com.endava.md.internship.parkinglot.utils.swagger.PreparedParkingLotDtoUtils;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking-lots")
@RequiredArgsConstructor
@Tag(name = "Parking Lot Controller",
        description = "Handles parking lot operations, including creating, deleting, retrieving, and managing users for parking lots.")
public class ParkingLotController {

    private final ParkingLotService parkingService;

    @Operation(
            summary = "Create Parking Lot",
            description = "This endpoint is responsible for creating the new parking lot",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ParkingLotRequestDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Parking lot created successfully",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ParkingLotResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_SUCCESS_RESPONSE_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "Invalid parking lot data during creation process",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_FAILED_RESPONSE_DTO)
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
                            description = "Internal server error during parking lot creation",
                            responseCode = "500",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseGenericErrorDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedServerErrorUtils.PREPARED_SERVER_ERROR_RESPONSE)
                            )
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<ParkingLotResponseDto> newParkingLot(@Valid @RequestBody final ParkingLotRequestDto parkingLotRequestDto) {
        ParkingLotResponseDto response = parkingService.createParkingLot(parkingLotRequestDto);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete parking lot",
            description = "This endpoint is responsible for delete an existing parking lot",
            parameters = @Parameter(name = "name", description = "Parking Lot Name", example = "Port Mall", required = true),
            responses = {
                    @ApiResponse(
                            description = "Parking lot removed successfully",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ParkingLotResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_SUCCESS_RESPONSE_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "Parking lot with passed name does not exist",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_PARKING_LOT_DOES_NOT_EXIST_DATA_DTO)
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
                            description = "Internal server error during parking lot removal",
                            responseCode = "500",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseGenericErrorDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedServerErrorUtils.PREPARED_SERVER_ERROR_RESPONSE)
                            )
                    )
            }
    )
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<ParkingLotResponseDto> removeParkingLot(@PathVariable String name) {
        ParkingLotResponseDto response = parkingService.deleteParkingLot(name);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get all parking lots",
            description = "This endpoint is responsible for get all parking lot entries",
            responses = {
                    @ApiResponse(
                            description = "Successfully executed get all parking lots request",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = List.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedParkingLotDtoUtils.PREPARED_PARKING_LOT_LIST_RESPONSE_DTO)
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
    @GetMapping
    public ResponseEntity<List<ParkingLotGeneralDetailsDto>> getAllParkingLots() {
        List<ParkingLotGeneralDetailsDto> parkingLots = parkingService.getAllParkingLots();
        return ResponseEntity.ok(parkingLots);
    }

    @Operation(
            summary = "User assigned to parking lot",
            description = "This endpoint is responsible for assigning user to parking lot",
            parameters = {@Parameter(name = "parkingLotId", description = "Id of parking lot to assign the user", example = "125"),
                    @Parameter(name = "userId", description = "User id for assigning to parking lot", example = "10")},
            responses = {
                    @ApiResponse(
                            description = "User successfully assigned",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ParkingLotResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_SUCCESS_RESPONSE_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "User with passed id is not found",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_USER_NOT_FOUND_RESPONSE_DTO)
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
                            description = "Internal server error during parking lot creation",
                            responseCode = "500",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseGenericErrorDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedServerErrorUtils.PREPARED_SERVER_ERROR_RESPONSE)
                            )
                    )
            }
    )
    @PostMapping("/{parkingLotId}/users/{userId}")
    public ResponseEntity<ParkingLotResponseDto> addUserToParkingLot(@PathVariable Long parkingLotId, @PathVariable Long userId) {
        ParkingLotResponseDto response = parkingService.addUserToParkingLot(userId, parkingLotId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Remove user from parking lot",
            description = "This endpoint is responsible for remove user from parking lot",
            parameters = {@Parameter(name = "parkingLotId", description = "Parking lot id", example = "125"),
                    @Parameter(name = "userId", description = "User id for delete from parking lot", example = "10")},
            responses = {
                    @ApiResponse(
                            description = "User successfully removed from parking lot",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ParkingLotResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_SUCCESS_RESPONSE_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "User with passed id is not found",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_USER_NOT_FOUND_RESPONSE_DTO)
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
                            description = "Internal server error during removing user from parking lot",
                            responseCode = "500",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseGenericErrorDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedServerErrorUtils.PREPARED_SERVER_ERROR_RESPONSE)
                            )
                    )
            }
    )
    @DeleteMapping("/{parkingLotId}/users/{userId}")
    public ResponseEntity<ParkingLotResponseDto> removeUserFromParkingLot(@PathVariable Long parkingLotId, @PathVariable Long userId) {
        ParkingLotResponseDto response = parkingService.removeUserFromParkingLot(userId, parkingLotId);
        return ResponseEntity.ok(response);
    }
}