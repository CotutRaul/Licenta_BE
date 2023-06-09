package uvt.cotut.licenta_be.service.api.dto;

import lombok.Data;

@Data
public class ProductDisplayDTO {
    private Long id;
    private String name;
    private Float price;
    private Float originalPrice;
//    private Long amount;
    private String imageCardPath;
}
