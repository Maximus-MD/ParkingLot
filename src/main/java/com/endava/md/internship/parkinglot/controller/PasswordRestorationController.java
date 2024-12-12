package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ResponseDTO;
import com.endava.md.internship.parkinglot.dto.ResponseGenericErrorDTO;
import com.endava.md.internship.parkinglot.utils.ResponseFactory;
import com.endava.md.internship.parkinglot.dto.ResponseMessageDTO;
import com.endava.md.internship.parkinglot.service.PasswordRestorationService;
import com.endava.md.internship.parkinglot.utils.swagger.PreparedResponseDtoUtils;
import com.endava.md.internship.parkinglot.utils.swagger.PreparedResponseMessageDto;
import com.endava.md.internship.parkinglot.utils.swagger.PreparedServerErrorUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/restore-password")
@Tag(name = "Password Restoration Controller",
        description = "Handles password restoration requests for users. Allows users to restore their password via email.")
public class PasswordRestorationController {

    private final PasswordRestorationService passwordRestorationService;

    public PasswordRestorationController(PasswordRestorationService passwordRestorationService) {
        this.passwordRestorationService = passwordRestorationService;
    }

    @Operation(
            summary = "User password restoration",
            description = "This endpoint is responsible for executing user password restoration process",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ResponseMessageDTO.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Successfully restored user password",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseMessageDto.PREPARED_SUCCESS_RESPONSE_MESSAGE_DTO)
                            )
                    ),
                    @ApiResponse(
                            description = "User with passed email is not found",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseDtoUtils.PREPARED_FAILED_RESPONSE_DTO_WHEN_USER_IS_NOT_FOUND)
                            )
                    ),
                    @ApiResponse(
                            description = "Internal server error during password restoration process",
                            responseCode = "500",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseGenericErrorDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedServerErrorUtils.PREPARED_SERVER_ERROR_RESPONSE)
                            )
                    )
            }
    )
    @PatchMapping
    protected ResponseEntity<ResponseMessageDTO> passwordRestoration(@Valid @RequestBody UserEmailDTO userEmail) throws MessagingException {
        passwordRestorationService.restorePassword(userEmail.email);
        return ResponseFactory.createResponse(userEmail.email, "Restored");
    }

    protected record UserEmailDTO(
            @NotBlank(message = "{message.invalid-email}")
            @Email(message = "{message.invalid-email}")
            @Schema(description = "The user's email address. It must be a valid email format and cannot be blank.",
                    example = "user@example.com")
            String email
    ) {
    }
}
