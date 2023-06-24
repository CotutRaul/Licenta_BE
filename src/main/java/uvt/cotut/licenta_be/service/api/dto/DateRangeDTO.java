package uvt.cotut.licenta_be.service.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DateRangeDTO {
    private LocalDateTime[] dates;
}
