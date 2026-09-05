package FakeCommerceApp.demo.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FakeCommerceApp.demo.DTO.DTOCategory;
import FakeCommerceApp.demo.Services.CategoryService;
import FakeCommerceApp.demo.schema.Category;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody DTOCategory requestDTO){
        // return CategoryService.createCategory(requestDTO);
        return  ResponseEntity
                // .status(HttpStatusCode.valueOf(201))
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(requestDTO));
    }

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deletCategory(id);
    }
}
