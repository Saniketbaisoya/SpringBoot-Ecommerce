package FakeCommerceApp.demo.DTO;

import java.time.LocalDateTime;
import java.util.List;

import FakeCommerceApp.demo.schema.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DTOResponseForOrder {
    
    private Long id;

    private OrderStatus order_status;

    List<DTOResponseForOrderItem> items;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
