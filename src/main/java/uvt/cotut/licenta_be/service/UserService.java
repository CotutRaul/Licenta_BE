package uvt.cotut.licenta_be.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uvt.cotut.licenta_be.exception.ApplicationBusinessException;
import uvt.cotut.licenta_be.exception.ErrorCode;
import uvt.cotut.licenta_be.model.Role;
import uvt.cotut.licenta_be.model.User;
import uvt.cotut.licenta_be.repository.UserRepository;
import uvt.cotut.licenta_be.service.api.UserMapper;
import uvt.cotut.licenta_be.service.api.dto.UserCredentialsDTO;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Transactional
    public User addClient(UserCredentialsDTO credentialsDTO) {
        if( userRepository.findUserByEmail(credentialsDTO.getEmail()) != null ){
            throw new ApplicationBusinessException(ErrorCode.USER_ALREADY_TAKEN);
        }
        else {
            User client = userMapper.toEntity(credentialsDTO);
            client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
            client.setRole(Role.CLIENT);
            return userRepository.save(client);
        }
    }
}
