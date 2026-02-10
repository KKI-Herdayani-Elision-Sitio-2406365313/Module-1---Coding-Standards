package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryEditDeleteTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindByIdPositive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product foundProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertNotNull(foundProduct);
        assertEquals(product.getProductId(), foundProduct.getProductId());
        assertEquals(product.getProductName(), foundProduct.getProductName());
        assertEquals(product.getProductQuantity(), foundProduct.getProductQuantity());
    }

    @Test
    void testFindByIdNegative() {
        Product foundProduct = productRepository.findById("non-existent-id");

        assertNull(foundProduct);
    }

    @Test
    void testUpdateProductPositive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang Updated");
        updatedProduct.setProductQuantity(150);

        Product result = productRepository.update(updatedProduct);

        assertNotNull(result);
        assertEquals("Sampo Cap Bambang Updated", result.getProductName());
        assertEquals(150, result.getProductQuantity());

        Product fetchedProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("Sampo Cap Bambang Updated", fetchedProduct.getProductName());
        assertEquals(150, fetchedProduct.getProductQuantity());
    }

    @Test
    void testUpdateProductNegative() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("Ghost Product");
        updatedProduct.setProductQuantity(999);

        Product result = productRepository.update(updatedProduct);

        // Verify
        assertNull(result);
    }


    @Test
    void testDeleteProductPositive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        assertNotNull(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6"));

        boolean result = productRepository.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertTrue(result);
        assertNull(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }

    @Test
    void testDeleteProductNegative() {
        boolean result = productRepository.delete("non-existent-id");

        assertFalse(result);
    }

    @Test
    void testDeleteProductFromMultiple() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        Product product3 = new Product();
        product3.setProductId("id-3");
        product3.setProductName("Product 3");
        product3.setProductQuantity(300);
        productRepository.create(product3);

        boolean result = productRepository.delete("id-2");

        assertTrue(result);
        assertNotNull(productRepository.findById("id-1"));
        assertNull(productRepository.findById("id-2"));
        assertNotNull(productRepository.findById("id-3"));
    }
}