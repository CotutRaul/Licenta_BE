package uvt.cotut.licenta_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uvt.cotut.licenta_be.exception.ApplicationBusinessException;
import uvt.cotut.licenta_be.service.api.dto.AuthenticationResponse;
import uvt.cotut.licenta_be.service.api.dto.UserCredentialsDTO;
import uvt.cotut.licenta_be.service.composite.AuthenticationCompositeService;

@RestController
@RequestMapping("/authenticate")
@AllArgsConstructor
@Tag(name = "Authentication")
@Slf4j
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationCompositeService authenticationCompositeService;

    @Operation(summary = "Login user with credentials")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    public AuthenticationResponse authenticateLogin(@RequestBody @Valid UserCredentialsDTO credentialsDTO) throws ApplicationBusinessException {
        return authenticationCompositeService.authenticateLogin(credentialsDTO);
    }

    @Operation(summary = "Register user with credentials")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "Unauthorized Feature"),
            @ApiResponse(responseCode = "500", description = "Server Error"),})
    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    public AuthenticationResponse authenticateRegister(@RequestBody @Valid UserCredentialsDTO credentialsDTO) throws ApplicationBusinessException {
        return authenticationCompositeService.authenticateRegister(credentialsDTO);
    }
}
