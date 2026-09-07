package FakeCommerceApp.demo.Adapters;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import FakeCommerceApp.demo.DTO.DTOResponseForOrder;
import FakeCommerceApp.demo.DTO.DTOResponseForOrderItem;
import FakeCommerceApp.demo.Repositories.OrderProductRepository;
import FakeCommerceApp.demo.schema.OrderProduct;
import FakeCommerceApp.demo.schema.OrderSchema;

public class OrderAdapter {
    
    /**
     * Now items hmara bnega from the orderProducts, now OrderSchema mai koi bhi orderProduct related relationship nhi hai...
     * Now, but agr mai orderProduct Schema mai jake dekhu toh vha pr hmme milega Order relation
     * So now we can use the orderProductRepository for fetch all the orders by the order_id giving the order.getId()
     * Now isse jb hmme orders milege toh usme products bhi hoge corresponding to that order_id in the table of orderProduct
     * Now orderProduct ki hmme puri list milegi and usko items mai, set nhi kr skta kyuki items ka type DTOResponseForOrderItems ka hai
     * Then next function hmme mapOrderProductsToDTOResponseForOrderItems ka bnana bnega kyuki yeah sari mapping krega by adapter pattern
     * And isse hmm repeated task se bach jayege....
    */
    private OrderProductRepository orderProductRepository;

    public  List<DTOResponseForOrder> mapOrderToDTOResponseForOrder(List<OrderSchema> orders){
        return  orders.stream()
                .map(this::mapOrderToDTOResponseForOrder)
                .collect(Collectors.toList());
    }

    public DTOResponseForOrder mapOrderToDTOResponseForOrder( OrderSchema order){
        List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(order.getId());
        
        List<DTOResponseForOrderItem> items = mapOrderProductsToDTOResponseForOrderItems(orderProducts);
        return DTOResponseForOrder.builder()
                .id(order.getId())
                .order_status(order.getOrderStatus())
                .created_at(order.getCreatedAt())
                .updated_at(order.getUpdatedAt())
                .items(items)
                .build();
    }

    public List<DTOResponseForOrderItem> mapOrderProductsToDTOResponseForOrderItems(List<OrderProduct> orderProduct){

        return  orderProduct.stream()
                .map(op ->  DTOResponseForOrderItem.builder()
                            .productName(op.getProduct().getTitle())
                            .productId(op.getProduct().getId())
                            .productImage(op.getProduct().getImage())
                            .productPrice(op.getProduct().getPrice())
                            .quantity(op.getQuantity())
                            .subTotal(op.getProduct().getPrice().multiply(BigDecimal.valueOf(op.getQuantity())))
                            .build())
                .collect(Collectors.toList());
    }
}
