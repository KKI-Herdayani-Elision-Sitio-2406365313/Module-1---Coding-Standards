package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d/product", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/list");

        WebElement createButton = driver.findElement(By.linkText("Create Product"));
        createButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/create"));

        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        productNameInput.clear();
        productNameInput.sendKeys("Test Product Selenium");

        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        productQuantityInput.clear();
        productQuantityInput.sendKeys("99");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/list"));

        WebElement productTable = driver.findElement(By.tagName("table"));
        List<WebElement> rows = productTable.findElements(By.tagName("tr"));

        assertTrue(rows.size() >= 2);

        boolean productFound = false;
        for (WebElement row : rows) {
            if (row.getText().contains("Test Product Selenium")) {
                productFound = true;
                assertTrue(row.getText().contains("99"));
                break;
            }
        }
        assertTrue(productFound, "Created product should appear in the list");
    }
}