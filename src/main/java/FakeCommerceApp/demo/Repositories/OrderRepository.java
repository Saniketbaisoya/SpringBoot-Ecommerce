package FakeCommerceApp.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import FakeCommerceApp.demo.schema.OrderSchema;

public interface OrderRepository extends JpaRepository<OrderSchema, Long>{

    
}