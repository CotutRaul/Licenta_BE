package uvt.cotut.licenta_be.util;

import lombok.Data;

@Data
public class ProductSalesStats {
    private Integer unitsSold;
    private Integer unitsDiscounted;
    private Float totalPrice;
    private Float totalPriceDiscounts;

    public ProductSalesStats() {
        this.unitsSold = 0;
        this.unitsDiscounted = 0;
        this.totalPrice = 0f;
        this.totalPriceDiscounts = 0f;
    }
}
