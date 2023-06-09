package uvt.cotut.licenta_be.service.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCreateDTO {
    @NotBlank(message = "NOT_BLANK, NAME")
    private String name;

    private String description;

    @Min(value = 0,message = "INVALID_VALUE, PRICE")
    @NotNull(message = "NOT_NULL, PRICE")
    private Float price;

    private Float originalPrice;

    @NotBlank(message = "NOT_BLANK, CATEGORY")
    private String category;

    @NotBlank(message = "NOT_BLANK, SUBCATEGORY")
    private String subCategory;

    private Boolean available;

    @NotNull(message = "NOT_NULL, IMAGE CARD PATH")
    private String imageCardPath;

}
