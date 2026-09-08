import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginTest {

    private static final String APP_PACKAGE  = "com.example.loginapp";
    private static final String APP_ACTIVITY = "com.example.loginapp.LoginActivity";

    private static final AppiumBy USERNAME   = (AppiumBy) AppiumBy.id(APP_PACKAGE + ":id/username");
    private static final AppiumBy PASSWORD   = (AppiumBy) AppiumBy.id(APP_PACKAGE + ":id/password");
    private static final AppiumBy LOGIN_BTN  = (AppiumBy) AppiumBy.id(APP_PACKAGE + ":id/loginButton");
    private static final AppiumBy CANCEL_BTN = (AppiumBy) AppiumBy.id(APP_PACKAGE + ":id/cancelButton");
    private static final AppiumBy WELCOME    = (AppiumBy) AppiumBy.id(APP_PACKAGE + ":id/welcomeText");
    private static final AppiumBy ERROR      = (AppiumBy) AppiumBy.id(APP_PACKAGE + ":id/errorText");

    private AndroidDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Android Device")
                .setAppPackage(APP_PACKAGE)
                .setAppActivity(APP_ACTIVITY)
                .setAppWaitActivity(APP_ACTIVITY)
                .setAutoGrantPermissions(true)
                .setNewCommandTimeout(Duration.ofSeconds(120));
        // To install a fresh build instead: options.setApp("/absolute/path/to/app-debug.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    private void enterCredentials(String user, String pass) {
        WebElement u = driver.findElement(USERNAME);
        u.clear();
        u.sendKeys(user);
        WebElement p = driver.findElement(PASSWORD);
        p.clear();
        p.sendKeys(pass);
        driver.hideKeyboard();
    }

    @Test
    @Order(1)
    @DisplayName("T1: valid credentials -> welcome screen")
    void validLogin() {
        enterCredentials("testuser", "Passw0rd!");
        driver.findElement(LOGIN_BTN).click();

        WebElement welcome = wait.until(ExpectedConditions.visibilityOfElementLocated(WELCOME));
        assertTrue(welcome.isDisplayed(), "Welcome view should be shown after valid login");
    }

    @Test
    @Order(2)
    @DisplayName("T2: wrong password -> error, stays on login screen")
    void wrongPassword() {
        enterCredentials("testuser", "wrong-password");
        driver.findElement(LOGIN_BTN).click();

        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR));
        assertTrue(error.isDisplayed(), "Error message should be shown");
        assertTrue(driver.findElement(USERNAME).isDisplayed(), "Should remain on login screen");
    }

    @Test
    @Order(3)
    @DisplayName("T3: cancel clears username and password")
    void cancelClearsForm() {
        enterCredentials("somebody", "something");
        driver.findElement(CANCEL_BTN).click();

        assertEquals("", driver.findElement(USERNAME).getText().trim(), "Username should be cleared");
        assertEquals("", driver.findElement(PASSWORD).getText().trim(), "Password should be cleared");
    }
}
