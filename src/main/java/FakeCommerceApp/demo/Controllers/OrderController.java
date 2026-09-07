package FakeCommerceApp.demo.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FakeCommerceApp.demo.DTO.DTOResponseForOrder;
import FakeCommerceApp.demo.Services.OrderService;
import FakeCommerceApp.demo.utils.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DTOResponseForOrder>>> getAllOrders() {
        List<DTOResponseForOrder> orders = orderService.getAllOrders();
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.SuccessResponse(orders, "SuccessFully fetched all the orders !!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DTOResponseForOrder>> getOrderById(@PathVariable Long id){
        DTOResponseForOrder order = orderService.getOrderById(id);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.SuccessResponse(order, "SuccessFully fetched the order with id : " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrderById(@PathVariable Long id){
        orderService.deleteOrder(id);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.SuccessResponse(null, "SuccessFully deleted the order with id : " + id));
    }
}
