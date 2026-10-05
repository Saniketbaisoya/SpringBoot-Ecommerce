package FakeCommerceApp.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;

import FakeCommerceApp.demo.Repositories.ProductRepository;
import FakeCommerceApp.demo.config.TestJpaConfig;
import FakeCommerceApp.demo.schema.Category;
import FakeCommerceApp.demo.schema.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestJpaConfig.class)
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    private Category category;
    private Product product;
    
    @BeforeEach
    void setUp(){
        // arrange
        category = Category.builder().name("Test Category").build();
        product = Product.builder()
                  .title("Test Product")
                  .description("Test Product description")
                  .price(BigDecimal.valueOf(999.0).setScale(2, RoundingMode.HALF_UP))
                  .image("Test Product image")
                  .rating("100")
                  .category(category)
                  .build();
        
        testEntityManager.persistAndFlush(category);
        testEntityManager.persistAndFlush(product);

        testEntityManager.clear();

    }

    @Test
    void findProductById_whenFound_returnProductWithCategory(){

        // act.....
        List<Product> result = productRepository.findProductByIdWithDetails(product.getId());

        // assert.....
        assertEquals(1, result.size());
        assertEquals(category, result.get(0).getCategory());
        assertEquals(product.getTitle(), result.get(0).getTitle());
        assertEquals(product.getDescription(), result.get(0).getDescription());
        assertEquals(product.getPrice(), result.get(0).getPrice());
        assertEquals(product.getImage(), result.get(0).getImage());
        assertEquals(product.getRating(), result.get(0).getRating());
        assertEquals(product.getCategory().getName(), result.get(0).getCategory().getName());

    }
}
