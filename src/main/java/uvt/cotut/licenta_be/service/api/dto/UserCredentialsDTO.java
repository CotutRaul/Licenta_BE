package uvt.cotut.licenta_be.service.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCredentialsDTO {
    @NotBlank(message = "NOT_BLANK, EMAIL")
    private String email;

    @NotBlank(message = "NOT_BLANK, PASSWORD")
    @Size(min = 8, message = "INVALID_SIZE, PASSWORD")
    private String password;

    private String name;
    private String surname;

}
