package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testEditProduct() {
        Product product = new Product();
        product.setProductId("Product id");
        product.setProductName("Product Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("Product id");
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(20);

        productRepository.update(updatedProduct);
        Product result = productRepository.findById("Product id");

        assertEquals("Updated Name", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testEditNonExistentProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non existent");
        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId("delete product");
        productRepository.create(product);

        boolean deleted = productRepository.delete("delete product");
        assertTrue(deleted);
        assertNull(productRepository.findById("delete product"));
    }

    @Test
    void testDeleteNonExistentProduct() {
        boolean deleted = productRepository.delete("non existent");
        assertFalse(deleted);
    }

    @Test
    void testCreateWithNullProductId() {
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("No ID Product");
        product.setProductQuantity(5);

        Product result = productRepository.create(product);

        assertNotNull(result.getProductId());
        assertFalse(result.getProductId().isEmpty());
    }

    @Test
    void testCreateWithEmptyProductId() {
        Product product = new Product();
        product.setProductId("");
        product.setProductName("Empty ID Product");
        product.setProductQuantity(5);

        Product result = productRepository.create(product);
        assertNotNull(result.getProductId());
        assertFalse(result.getProductId().isEmpty());
    }

    @Test
    void testFindByIdNotFound() {
        Product product = new Product();
        product.setProductId("existing id");
        product.setProductName("Existing Product");
        product.setProductQuantity(5);
        productRepository.create(product);
        Product result = productRepository.findById("non existent id");
        assertNull(result);
    }

    @Test
    void testUpdateNonExistentProductInNonEmptyRepository() {
        Product product = new Product();
        product.setProductId("existing id");
        product.setProductName("Existing");
        product.setProductQuantity(10);
        productRepository.create(product);
        Product updatedProduct = new Product();
        updatedProduct.setProductId("nonexistentId");
        updatedProduct.setProductName("Nonexistent Product");
        updatedProduct.setProductQuantity(99);
        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }



}