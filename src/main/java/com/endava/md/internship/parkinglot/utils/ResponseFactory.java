package com.endava.md.internship.parkinglot.utils;

import com.endava.md.internship.parkinglot.dto.ResponseDTO;
import com.endava.md.internship.parkinglot.dto.ResponseMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Set;

public final class ResponseFactory {

    private ResponseFactory() {
    }

    public static ResponseEntity<ResponseDTO> createResponse(final String token) {
        return ResponseEntity.ok(new ResponseDTO(true, token, Collections.emptySet()));
    }

    public static ResponseEntity<ResponseMessageDTO> createResponse(final String email, final String message) {
        return ResponseEntity.ok(new ResponseMessageDTO(true, email, message));
    }

    public static ResponseEntity<ResponseMessageDTO> createResponse(final ResponseMessageDTO responseMessageDTO) {
        return ResponseEntity.ok(responseMessageDTO);
    }

    public static ResponseEntity<ResponseDTO> createResponse(final Set<Integer> errors) {
        return ResponseEntity.ok(new ResponseDTO(false, null, errors));
    }

    public static ResponseEntity<ResponseDTO> createResponse(final ResponseDTO responseDTO) {
        return ResponseEntity.ok(responseDTO);
    }

    public static ResponseEntity<ResponseDTO> createResponse(final Integer error) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(false, null, Collections.singleton(error)));
    }
}
