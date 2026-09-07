package FakeCommerceApp.demo.Controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import FakeCommerceApp.demo.utils.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductServices productServices;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts(){
        // return productServices.getAllProducts();
        List<Product> products = productServices.getAllProducts();
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.SuccessResponse(products, "SuccessFully fetched all products !!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DTOResponse>> getProductById(@PathVariable Long id){
        // return productServices.getProductById(id);
        DTOResponse productResponse = productServices.getProductById(id);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.SuccessResponse(productResponse, "SuccessFully Fetched the product with id : " + id));
    }

    @GetMapping("/{id}/category")
    public ResponseEntity<ApiResponse<DTOResponseForProduct>> getProductByIdWithDetails(@PathVariable Long id){
        // return productServices.getProductByIdWithDetail(id);
        DTOResponseForProduct productResponse = productServices.getProductByIdWithDetail(id);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.SuccessResponse(productResponse, "SuccessFully fetched the product with id : " + id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody DTOProduct requestDTO){
        Product product = productServices.createProduct(requestDTO);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.SuccessResponse(product, "SuccessFully fetched the product !!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id){
        productServices.deleteProduct(id);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.SuccessResponse(null, "SuccessFully deleted the product with id : " + id));
    }

    // @GetMapping("/category/{id}")
    // public String getCategoryName(@PathVariable Long id){
    //     return productServices.categoryName(id);
    // }
}
