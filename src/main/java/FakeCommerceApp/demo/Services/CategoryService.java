package FakeCommerceApp.demo.Services;



import java.util.List;

import org.springframework.stereotype.Service;

import FakeCommerceApp.demo.DTO.DTOCategory;
import FakeCommerceApp.demo.Repositories.CategoryRepository;
import FakeCommerceApp.demo.schema.Category;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    public Category createCategory(DTOCategory requestDTO){
        Category category = Category.builder()
                            .name(requestDTO.getName())
                            .build();
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void deletCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
