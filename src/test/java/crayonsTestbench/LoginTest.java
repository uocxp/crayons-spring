package crayonsTestbench;


import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.testbench.By;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.FormLayoutElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TextFieldElement;
import com.vaadin.testbench.parallel.ParallelTest;


public class LoginTest extends TestBenchTestCase {

    // -------MAVEN-------------
    /*
     * 
     * <dependency> <groupId>com.vaadin</groupId>
     * <artifactId>vaadin-testbench</artifactId> <version>4.1.0.beta2</version>
     * <scope>test</scope> </dependency>
     */
    // ------------------------

    // private static final String URL = "http://localhost";
    // private static final String PORT = "8080";

    private ResourceBundle lang = LanguageService.getInstance().getRes();

    @Before
    public void setup() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        setDriver(TestBench.createDriver(new ChromeDriver()));
        // Open the WebPage
        getDriver().get("http://localhost:8080");
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() throws Exception {
        // close the browser window
        getDriver().quit();
    }

    @Test
    public void test() {

        String testEMail = "user@mail.com";
        String testPassword = "txtLogin";

      
        
        // 1. Enter eMail < > into the eMail-Login field
        $(TextFieldElement.class).caption("Anmelden: ").first().setValue("client@web.de");
        
        
        
        // 2. Enter password "123456" into the password-Login field
        $(PasswordFieldElement.class).caption("Passwort: ").first().setValue("123455");

        // 3. Click the "Login" button
        $(ButtonElement.class).caption(lang.getString("Login")).first().click();

    }

}