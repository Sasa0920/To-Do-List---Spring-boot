package com.example.todolist.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskUiTest {

    private WebDriver driver;

    @LocalServerPort
    private int port;

    @BeforeAll
    void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    private String baseUrl() {
        return "http://localhost:" + port + "/";
    }

    @Test
    void addTaskUi_shouldAddTask() {
        driver.get(baseUrl());
        WebElement input = driver.findElement(By.name("title"));
        input.sendKeys("Selenium Task");
        input.submit();

        try { Thread.sleep(400); } catch (InterruptedException ignored) {}

        boolean found = driver.findElements(By.xpath("//li//span[text()='Selenium Task']")).size() > 0;
        assertTrue(found, "New task should appear in the UI task list");
    }

    @Test
    void deleteTaskUi_shouldRemoveTask() {
        driver.get(baseUrl());
        WebElement input = driver.findElement(By.name("title"));
        input.sendKeys("Temporary Task");
        input.submit();

        try { Thread.sleep(400); } catch (InterruptedException ignored) {}

        WebElement deleteButton = driver.findElement(By.xpath("//li[.//span[text()='Temporary Task']]//button[.//i[contains(@class,'fa-trash')]]"));
        deleteButton.click();

        try { Thread.sleep(400); } catch (InterruptedException ignored) {}

        boolean stillPresent = driver.findElements(By.xpath("//li//span[text()='Temporary Task']")).size() > 0;
        assertFalse(stillPresent, "Task should be deleted from UI");
    }
}
