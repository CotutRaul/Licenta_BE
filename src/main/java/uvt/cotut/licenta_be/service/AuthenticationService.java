package uvt.cotut.licenta_be.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uvt.cotut.licenta_be.exception.ApplicationBusinessException;
import uvt.cotut.licenta_be.exception.ErrorCode;
import uvt.cotut.licenta_be.model.User;
import uvt.cotut.licenta_be.service.api.dto.AuthenticationResponse;
import uvt.cotut.licenta_be.service.api.dto.UserCredentialsDTO;
import uvt.cotut.licenta_be.util.JwtUtil;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtTokenUtil;

    public AuthenticationResponse authenticateLogin(UserCredentialsDTO authenticationRequest) throws ApplicationBusinessException{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));

        final User userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());
        if(userDetails == null) {
            throw new ApplicationBusinessException(ErrorCode.USER_NOT_FOUND);
        }
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return new AuthenticationResponse(jwt);
    }

    @Transactional
    public AuthenticationResponse authenticateRegister(UserCredentialsDTO credentialsDTO) throws ResponseStatusException {
        if(userService.addClient(credentialsDTO) != null){
            return authenticateLogin(credentialsDTO);
        }
        throw new ApplicationBusinessException(ErrorCode.USER_ALREADY_TAKEN);
    }

}
