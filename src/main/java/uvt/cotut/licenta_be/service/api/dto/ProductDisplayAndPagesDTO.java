package uvt.cotut.licenta_be.service.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDisplayAndPagesDTO {
    private List<ProductDisplayDTO> productDisplayList;
    private Integer pages;
}
