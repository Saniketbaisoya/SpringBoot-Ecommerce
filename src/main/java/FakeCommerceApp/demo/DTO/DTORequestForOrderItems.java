package FakeCommerceApp.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DTORequestForOrderItems {
    
    private Long productId;

    private Integer quantity;
}
