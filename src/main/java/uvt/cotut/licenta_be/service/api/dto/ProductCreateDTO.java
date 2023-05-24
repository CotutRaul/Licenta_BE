package uvt.cotut.licenta_be.service.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uvt.cotut.licenta_be.model.SubCategory;

@Data
public class ProductCreateDTO {
    @NotBlank(message = "NOT_BLANK, NAME")
    private String name;

    private String description;

    @Min(value = 0,message = "INVALID_VALUE, PRICE")
    @NotNull(message = "NOT_NULL, PRICE")
    private Float price;

    @Min(value = 0, message = "INVALID_VALUE, AMOUNT")
    @NotNull(message = "NOT_NULL, AMOUNT")
    private Long amount;

    @NotNull(message = "NOT_NULL, CATEGORY")
    private SubCategory subCategory;

    @NotNull(message = "NOT_NULL, IMAGE CARD PATH")
    private String imageCardPath;

}
