package uvt.cotut.licenta_be.service.composite;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uvt.cotut.licenta_be.exception.ApplicationBusinessException;
import uvt.cotut.licenta_be.service.AuthenticationService;
import uvt.cotut.licenta_be.service.api.dto.AuthenticationResponse;
import uvt.cotut.licenta_be.service.api.dto.UserCredentialsDTO;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationCompositeService {
    private final AuthenticationService authenticationService;

    public AuthenticationResponse authenticateLogin(UserCredentialsDTO credentialsDTO) throws ApplicationBusinessException {
        return authenticationService.authenticateLogin(credentialsDTO);
    }

    public AuthenticationResponse authenticateRegister(UserCredentialsDTO credentialsDTO) throws ApplicationBusinessException{
        return authenticationService.authenticateRegister(credentialsDTO);

    }
}
