package FakeCommerceApp.demo.DTO;

import java.math.BigDecimal;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DTOProduct {
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private Long categoryId;
    private String rating;
}
