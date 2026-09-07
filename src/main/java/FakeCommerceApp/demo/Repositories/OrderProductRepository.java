package FakeCommerceApp.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import FakeCommerceApp.demo.schema.OrderProduct;

public interface  OrderProductRepository extends JpaRepository<OrderProduct, Long>{

    List<OrderProduct> findByOrderId(Long id);
}
