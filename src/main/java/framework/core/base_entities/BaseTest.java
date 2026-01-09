package framework.core.base_entities;


import framework.core.driver.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseTest {
    @BeforeMethod
    public void setUp() {
        DriverManager.init();
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quit();
    }
}
