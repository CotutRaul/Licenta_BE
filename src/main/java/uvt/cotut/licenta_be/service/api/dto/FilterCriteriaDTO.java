package uvt.cotut.licenta_be.service.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilterCriteriaDTO {
    private String name;
    private Float lowerPrice;
    private Float upperPrice;
    private List<String> category;
    private List<String> subCategory;
    private Boolean inStock;
    private String sort;
    private Integer page;
}
