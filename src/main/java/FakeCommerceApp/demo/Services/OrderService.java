package FakeCommerceApp.demo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import FakeCommerceApp.demo.Adapters.OrderAdapter;
import FakeCommerceApp.demo.DTO.DTOResponseForOrder;
import FakeCommerceApp.demo.Repositories.OrderProductRepository;
import FakeCommerceApp.demo.Repositories.OrderRepository;
import FakeCommerceApp.demo.Repositories.ProductRepository;
import FakeCommerceApp.demo.exceptions.ResourceNotFoundException;
import FakeCommerceApp.demo.schema.OrderSchema;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
@RequiredArgsConstructor 
public class OrderService {
    private OrderRepository orderRepository;
    private OrderProductRepository orderProductRepository;
    private ProductRepository productRepository;

    private OrderAdapter orderAdapter;

    public List<DTOResponseForOrder> getAllOrders() {
        
        List<OrderSchema> orders = orderRepository.findAll();
        return orderAdapter.mapOrderToDTOResponseForOrder(orders);
    }

    public DTOResponseForOrder getOrderById (Long id){
        
        OrderSchema order =  orderRepository.findById(id)
                             .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id : " + id));
        return orderAdapter.mapOrderToDTOResponseForOrder(order);
    }

    public void deleteOrder (Long id){
        OrderSchema order = orderRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id : " + id));
        orderRepository.deleteById(id);
    }
}
