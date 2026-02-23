package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("test id");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);
        Product result = productService.create(product);
        assertEquals(product, result);
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        List<Product> products = Arrays.asList(product);
        Iterator<Product> iterator = products.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();
        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
    }

    @Test
    void testFindById() {
        when(productRepository.findById("test id")).thenReturn(product);
        Product result = productService.findById("test id");
        assertEquals(product, result);
    }

    @Test
    void testFindByIdNotFound() {
        when(productRepository.findById("nonexistent")).thenReturn(null);
        Product result = productService.findById("nonexistent");
        assertNull(result);
    }

    @Test
    void testUpdate() {
        when(productRepository.update(product)).thenReturn(product);
        Product result = productService.update(product);
        assertEquals(product, result);
    }

    @Test
    void testDelete() {
        when(productRepository.delete("test id")).thenReturn(true);
        boolean result = productService.delete("test id");
        assertTrue(result);
    }

    @Test
    void testDeleteNonExistent() {
        when(productRepository.delete("nonexistent")).thenReturn(false);
        boolean result = productService.delete("nonexistent");
        assertFalse(result);
    }
}