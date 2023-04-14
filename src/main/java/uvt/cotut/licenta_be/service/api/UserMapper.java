package uvt.cotut.licenta_be.service.api;

import org.mapstruct.Mapper;
import uvt.cotut.licenta_be.model.User;
import uvt.cotut.licenta_be.service.api.dto.UserCredentialsDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCredentialsDTO toCredentialDTO(User user);
    User toEntity(UserCredentialsDTO credentialsDTO);

}
