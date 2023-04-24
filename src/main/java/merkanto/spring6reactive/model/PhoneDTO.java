package merkanto.spring6reactive.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneDTO {

    private Integer id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String phoneName;

    @Size(min = 1, max = 255)
    private String phoneStyle;

    @Size(max = 25)
    private String imei;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
