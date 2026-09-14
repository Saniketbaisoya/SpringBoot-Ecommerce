package FakeCommerceApp.demo.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import FakeCommerceApp.demo.Adapters.OrderAdapter;
import FakeCommerceApp.demo.DTO.DTORequestForOrder;
import FakeCommerceApp.demo.DTO.DTOResponseForOrder;
import FakeCommerceApp.demo.Repositories.OrderProductRepository;
import FakeCommerceApp.demo.Repositories.OrderRepository;
import FakeCommerceApp.demo.Repositories.ProductRepository;
import FakeCommerceApp.demo.exceptions.ResourceNotFoundException;
import FakeCommerceApp.demo.schema.OrderProduct;
import FakeCommerceApp.demo.schema.OrderSchema;
import FakeCommerceApp.demo.schema.OrderStatus;
import FakeCommerceApp.demo.schema.Product;
import jakarta.transaction.Transactional;
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

    /**
     * Now N = for fetch the product N times if the DTORequestOrder has N items 
     * Then N = means every time we save the orderProduct after building the builder pattern.....
     * N + N + 1 = 2N + 1 = N + 1;
     * Toh iss tarike se N + 1 query problem yha prr aagyi
    */

    // public DTOResponseForOrder createOrder( DTORequestForOrder requestForOrder){
    //     OrderSchema order = OrderSchema.builder()
    //                         .orderStatus(OrderStatus.PENDING)
    //                         .build();

    //     orderRepository.save(order); // 1 query save krne ki excecute hogi....

    //     if(requestForOrder.getItems() != null){
    //         // N items hai -> hmm N times product ko fetch krege because of the for loop...
    //         // Then N times hmm orderProduct bnakr save bhi krege 
    //         for(var itemsDTO : requestForOrder.getItems()){
    //             Product product =  productRepository.findById(itemsDTO.getProductId())
    //                                . orElseThrow( () -> new ResourceNotFoundException("Product not found for the given id: " + itemsDTO.getProductId()));

    //             OrderProduct orderProduct = OrderProduct.builder()
    //                                         .order(order)
    //                                         .product(product)
    //                                         .quantity(itemsDTO.getQuantity())
    //                                         .build();

    //             orderProductRepository.save(orderProduct);

    //         }
    //     }
    //     return orderAdapter.mapOrderToDTOResponseForOrder(order);
    // }

    /**
     * Now yha sirf 3 queries hi excecute ho rhi hai....
     * First order ko save krne ki orderRepository mai with pending state jo hmne, OrderStatus Schema se liya tha...
     * Then hmne second query sare products ko batch mai fetch kiya tb excecute kiya tha....
     * Then hne third query orderProducts ki arrayList ko save krne ke liye inside the orderProductRepository excecute ki query ko by saveALL, jisne batch mai puri ArrayList of orderProducts ko save krdiya inside the orderProductRepository....
    */
    @Transactional
    public DTOResponseForOrder createOrder( DTORequestForOrder dtoRequestForOrder){
        OrderSchema order =  OrderSchema.builder()
                            .orderStatus(OrderStatus.PENDING)
                            .build();

        orderRepository.save(order); // 1 query excecute here....

        if(dtoRequestForOrder.getItems() != null){
            /**
             * Now abb yha prr hmne stream function ka use krke sequence by sequence DTORequestForOrder mai items(DTORequestForOrderItems) mai se item ke anr se map function lgakrr product ki id li....
             * And usko collect kiya inside the list, by using the collect of Collectors.toList() ka use krke....
             * And then aise hi stream lga lga ke sare items ko lekrr sbke andr se productId nikal kr hmne list mai dalkr productIds ko dedi....
            */
            List<Long> productIds = dtoRequestForOrder.getItems().stream().map(item -> item.getProductId()).collect(Collectors.toList());

            /**
             * Now abb yha hmm sare productIds ka ek sth use krke ek query excecute krke, Sare products ko ek query mai fetch kr lege...
             * Using the findAllById ka use krke, it is the generic T based function, using which we can get the batch data
            */
            List<Product> products = productRepository.findAllById(productIds); // 1 query excecute here....

            /**
             * Now yha prr hmm every products jo batch mai upr fetch hue hai....
             * Sbko unke hi id ke corressponding map krdege, using the hashMap....
             * so that ki jo bhi productIds jiski vje se hmne products ko batch mai fetch kiya tha voh sbb productIds shi hai or voh kisi product se belong krti hain....
            */
            Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));

            /**
             * Now abb yha prr hmm validations krege, mtlb ki jo product with ids map hua hai inside the productMap, voh sare productIds uss map ke function mai exist krti hai ki nhi....
             * Agr sare productIds hmari key point pr exists krti hai at productMap then we can assure ki sare products bhi hai usi map mai....
             * Then next hmm phr productMap ka use krke every ids of product ko lekr orderProduct bnayege....
            */
            for(var ids : productIds){
                if(!productMap.containsKey(ids)){
                    throw new ResourceNotFoundException("Product not found with id: " + ids);
                }
            }

            List<OrderProduct> orderProducts = new ArrayList<>();

            for(var itemsDTO : dtoRequestForOrder.getItems()){
                Product product = productMap.get(itemsDTO.getProductId());
                
                orderProducts.add(OrderProduct.builder()
                                 .order(order)
                                 .product(product)
                                 .quantity(itemsDTO.getQuantity())
                                 .build());
            }
            orderProductRepository.saveAll(orderProducts); // 1 query excecute here....
        }
    
        return orderAdapter.mapOrderToDTOResponseForOrder(order);
    }
}
