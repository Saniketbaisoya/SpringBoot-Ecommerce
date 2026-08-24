package FakeCommerceApp.demo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import FakeCommerceApp.demo.DTO.DTOProduct;
import FakeCommerceApp.demo.DTO.DTOResponse;
import FakeCommerceApp.demo.DTO.DTOResponseForProduct;
import FakeCommerceApp.demo.Repositories.ProductRepository;
import FakeCommerceApp.demo.schema.Category;
import FakeCommerceApp.demo.schema.Product;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServices {
    private final ProductRepository productRepository;

    /**
     * Now yha kyuki mai getAllProducts kr rha hu, isliye hme multiple products milege
     * And isi vje se List data structure ka use kiya taki sare Products store ho jaye ek hi bari mai
     * Now again products ke objects hi return hoge toh List ka data type Product Schema hi hoga
     * @return All Product objects...
     */
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    /**
     * Now yha maine ek glti krdi voh yeah thi ki product id se mai product ko access kruga
     * Now ek time pr ek hi id ayegi or ek hi product return hoga that means ki, List jo multiple product ko contain krta hai
     * Voh use nhi hoga and isi vje se line error aa rha tha...
     * Now product jb ayega toh sara data products  ki tables se ayega and usko voh hi variable or function store kr skta hai jo product schema se bna ho
     * Isliye getProductById ka return type Product hai....
     * @param id -> Product Id
     * @return -> complete Product Object
     */
    public DTOResponse getProductById(Long id){
        // return productRepository.findById(id)
        //     .orElseThrow(() -> new RuntimeException("Product not found"));
        Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found"));

        return DTOResponse.builder()
                .title(product.getTitle())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .rating(product.getRating())
                .build();
    }

    private final CategoryService categoryService;
    public Product createProduct(DTOProduct requestDTO){
        Category category = categoryService.getCategoryById(requestDTO.getCategoryId());
        Product newProduct = Product.builder()
                            .title(requestDTO.getTitle())
                            .description(requestDTO.getDescription())
                            .image(requestDTO.getImage())
                            .price(requestDTO.getPrice())
                            .category(category)
                            .rating(requestDTO.getRating())
                            .build();
        return productRepository.save(newProduct);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public DTOResponseForProduct getProductByIdWithDetail(Long id){
        Product product = productRepository.findProductByIdWithDetails(id).get(0);
        return DTOResponseForProduct.builder()
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .category(product.getCategory().getName())
                .rating(product.getRating())
                .build();
    }
    // public String categoryName(Long id){
    //     Product product = getProductById(id);

    //     return product.getCategory();
    // }
}
