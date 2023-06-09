package uvt.cotut.licenta_be.service.api.dto;

import jakarta.validation.Valid;
import lombok.Data;
import uvt.cotut.licenta_be.model.Address;

import java.util.List;

@Data
public class OrderCreateDTO {

    private List<CartDTO> cartDTOS;

    @Valid
    private Address address;
}
