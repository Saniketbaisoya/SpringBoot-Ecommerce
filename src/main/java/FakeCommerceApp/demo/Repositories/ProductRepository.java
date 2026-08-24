package FakeCommerceApp.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import FakeCommerceApp.demo.schema.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    // @Query(
    //     nativeQuery = true, 
    //     value = "SELECT p.*, c.name as category from products p INNER JOIN categories c on p.category_id = c.id where p.id = :id"
    // )
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.id = :id")
    List <Product> findProductByIdWithDetails(Long id);
    
}
