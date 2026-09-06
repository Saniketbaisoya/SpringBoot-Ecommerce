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
import FakeCommerceApp.demo.utils.ApiResponse;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody DTOCategory requestDTO){
        // return CategoryService.createCategory(requestDTO);
        // return  ResponseEntity
        //         // .status(HttpStatusCode.valueOf(201))
        //         .status(HttpStatus.CREATED)
        //         .body(categoryService.createCategory(requestDTO));
        Category category = categoryService.createCategory(requestDTO);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.SuccessResponse(category, "SuccessFully created the Category !!"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories(){
        // return categoryService.getAllCategories();
        List<Category> categories = categoryService.getAllCategories();
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.SuccessResponse(categories, "SuccessFully fetched All Categories !!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id){
        // return categoryService.getCategoryById(id);
        Category category = categoryService.getCategoryById(id);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.SuccessResponse(category, "SuccessFully fetched the Category !!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id){
        categoryService.deletCategory(id);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.SuccessResponse(null ,"SuccessFully delete the Category !!"));
    }
}
