package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.dto.ResponseDTO;
import com.endava.md.internship.parkinglot.dto.ResponseGenericErrorDTO;
import com.endava.md.internship.parkinglot.utils.ResponseFactory;
import com.endava.md.internship.parkinglot.service.UserService;
import com.endava.md.internship.parkinglot.utils.swagger.PreparedResponseDtoUtils;
import com.endava.md.internship.parkinglot.utils.swagger.PreparedServerErrorUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
@Tag(name = "Registration Controller",
        description = "Handles registration requests. Allows to create a new user account.")
public class RegistrationController {
    private final UserService userService;

    @Operation(
            summary = "User registration",
            description = "This endpoint is responsible for executing the registration process for the user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = RegistrationRequestDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Successful user registration",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_SUCCESS_RESPONSE_DTO_WITH_JWT)
                            )
                    ),
                    @ApiResponse(
                            description = "Invalid user data during registration process",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_INVALID_REGISTRATION_DATA_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "Internal server error during change user role process",
                            responseCode = "500",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseGenericErrorDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedServerErrorUtils.PREPARED_SERVER_ERROR_RESPONSE)
                            )
                    )
            }
    )
    @SecurityRequirements()
    @PostMapping
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody RegistrationRequestDto registrationDto) {
        return ResponseFactory.createResponse(userService.registerNewUser(registrationDto));
    }
}


