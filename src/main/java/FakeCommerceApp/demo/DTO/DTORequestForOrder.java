package FakeCommerceApp.demo.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DTORequestForOrder {
    
    private List<DTORequestForOrderItems> items;
}
