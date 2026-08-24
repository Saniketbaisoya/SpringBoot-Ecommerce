package FakeCommerceApp.demo.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FakeCommerceApp.demo.DTO.DTOProduct;
import FakeCommerceApp.demo.DTO.DTOResponse;
import FakeCommerceApp.demo.DTO.DTOResponseForProduct;
import FakeCommerceApp.demo.Services.ProductServices;
import FakeCommerceApp.demo.schema.Product;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductServices productServices;

    @GetMapping
    public List<Product> getAllProducts(){
        return productServices.getAllProducts();
    }

    @GetMapping("/{id}")
    public DTOResponse getProductById(@PathVariable Long id){
        return productServices.getProductById(id);
    }

    @GetMapping("/{id}/category")
    public DTOResponseForProduct getProductByIdWithDetails(@PathVariable Long id){
        return productServices.getProductByIdWithDetail(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody DTOProduct requestDTO){
        return productServices.createProduct(requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productServices.deleteProduct(id);
    }

    // @GetMapping("/category/{id}")
    // public String getCategoryName(@PathVariable Long id){
    //     return productServices.categoryName(id);
    // }
}
