package FakeCommerceApp.demo.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import FakeCommerceApp.demo.DTO.DTOCategory;
import FakeCommerceApp.demo.Repositories.CategoryRepository;
import FakeCommerceApp.demo.Services.CategoryService;
import FakeCommerceApp.demo.exceptions.ResourceDeletionException;
import FakeCommerceApp.demo.exceptions.ResourceNotFoundException;
import FakeCommerceApp.demo.schema.Category;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest { // run the test -> run this service test class
    
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void createCategory_savesAndReturnsCategory(){
        // arrange....
        DTOCategory dtoCategory = DTOCategory.builder().name("Test Category").build();
        Category testCategory = Category.builder().name("Test Category").build();
        testCategory.setId(1L);
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        // act....
        Category result = categoryService.createCategory(dtoCategory);

        // assert....
        assertEquals("Test Category", result.getName());
        assertEquals(1L, result.getId());
    };

    @Test
    void getCategoryById_whenFound_returnsCategory(){
        // arrange....
        Category testCategory = Category.builder().name("Test Category").build();
        testCategory.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

        // act.....
        Category results = categoryService.getCategoryById(1L);

        // assert.....
        assertEquals("Test Category", results.getName());
        assertEquals(1L, results.getId());
    };

    @Test
    void getCategoryById_whenNotFound_returnsResourceNotFoundException(){
        // arrange.....
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // act and assert.....
        assertThrows(ResourceNotFoundException.class, ()-> categoryService.getCategoryById(1L));
    };

    @Test
    void getAllCategories_whenFound_returnsCategory(){
        // arrange.....
        List<Category> testCategory = new ArrayList<>();
        
        Category category1 = Category.builder().name("Test Category 1").build();
        category1.setId(1L);
        testCategory.add(category1);

        Category category2 = Category.builder().name("Test Category 2").build();
        category2.setId(2L);
        testCategory.add(category2);

        when(categoryRepository.findAll()).thenReturn(testCategory);

        // act...
        List<Category> results = categoryService.getAllCategories();

        // assert....
        assertEquals(testCategory.size(), results.size());

        assertEquals("Test Category 1", results.get(0).getName());
        assertEquals(1L, results.get(0).getId());

        assertEquals("Test Category 2", results.get(1).getName());
        assertEquals(2L, results.get(1).getId());

    }

    @Test
    void deleteCategory_whenFound_returnsMessage(){
        // arrange.....
        Category testCategory = Category.builder().name("Test Category").build();
        testCategory.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

        // act.....
        categoryService.deletCategory(1L);

        // assert.....
        verify(categoryRepository).delete(testCategory);
    }

    @Test
    void deleteCategory_whenNotFound_returnResourceNotFoundException(){
        // arrange.....
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // act and assert.....
        assertThrows(ResourceDeletionException.class, ()-> categoryService.deletCategory(1L));
    }
}
