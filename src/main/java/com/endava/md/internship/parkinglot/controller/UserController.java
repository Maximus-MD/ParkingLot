package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ResponseDTO;
import com.endava.md.internship.parkinglot.dto.ResponseGenericErrorDTO;
import com.endava.md.internship.parkinglot.utils.ResponseFactory;
import com.endava.md.internship.parkinglot.dto.ResponseMessageDTO;
import com.endava.md.internship.parkinglot.service.UserService;
import com.endava.md.internship.parkinglot.utils.swagger.PreparedResponseDtoUtils;
import com.endava.md.internship.parkinglot.utils.swagger.PreparedResponseMessageDto;
import com.endava.md.internship.parkinglot.utils.swagger.PreparedServerErrorUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_ADMIN;
import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_REGULAR;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@Tag(name = "User Controller",
        description = "Handles user-related operations such as role management and user updates")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Changes the current user role to the admin role",
            description = "This endpoint is responsible for changing current user role to admin",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserEmailDTO.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "User role successfully changed to admin",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseMessageDto.PREPARED_SUCCESS_RESPONSE_MESSAGE_DTO_WITH_ADMIN_ROLE)
                            )
                    ),
                    @ApiResponse(
                            description = "User with passed email is not found",
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
    @PatchMapping("/switch-role/admin")
    protected ResponseEntity<ResponseMessageDTO> switchUserRoleToAdmin(@Valid @RequestBody UserEmailDTO userEmail) {
        return ResponseFactory.createResponse(userService.setNewRole(userEmail.email, ROLE_ADMIN));
    }

    @Operation(
            summary = "Changes the current user role to the regular role",
            description = "This endpoint is responsible for changing current user role to regular",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserEmailDTO.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Successfully changed user role to regular",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(PreparedResponseMessageDto.PREPARED_SUCCESS_RESPONSE_MESSAGE_DTO_WITH_REGULAR_ROLE)
                            )
                    ),
                    @ApiResponse(
                            description = "User with passed email is not found",
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
    @PatchMapping("/switch-role/regular")
    protected ResponseEntity<ResponseMessageDTO> switchUserRoleToRegular(@Valid @RequestBody UserEmailDTO userEmail) {
        return ResponseFactory.createResponse(userService.setNewRole(userEmail.email, ROLE_REGULAR));
    }

    protected record UserEmailDTO(
            @NotBlank(message = "{message.invalid-email}")
            @Email(message = "{message.invalid-email}")
            @Schema(description = "The email address of the user.", example = "user@example.com")
            String email
    ) {
    }
}