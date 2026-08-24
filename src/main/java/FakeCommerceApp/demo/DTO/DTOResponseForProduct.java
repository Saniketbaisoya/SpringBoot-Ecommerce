package FakeCommerceApp.demo.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class DTOResponseForProduct {
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private String category;
    private String rating;
}
