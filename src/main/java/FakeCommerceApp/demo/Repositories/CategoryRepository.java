package FakeCommerceApp.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import FakeCommerceApp.demo.schema.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    
}
