package com.kshirsa.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class UserPublicController {

    @Operation(summary = "To generate Otp for email validation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP Sent",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
    })
    @PostMapping("/generate")
    public ResponseEntity<String> generateEmailOtp(@RequestBody @NotNull String email) {

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
