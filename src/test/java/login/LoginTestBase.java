package login;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

/**
 * Shared Appium setup for the sample Login Form tests
 * (Username field, Password field, Login button, Cancel button).
 *
 * Assumptions (change to match your app):
 *  - App package/activity below.
 *  - Elements exposed via resource-id: username, password, loginButton, cancelButton.
 *  - Valid login shows resource-id "welcomeText"; invalid login shows "errorText".
 *  - Appium server at http://127.0.0.1:4723 with an Android device/emulator online.
 */
abstract class LoginTestBase {

    protected static final String APP_PACKAGE  = "com.example.loginapp";
    protected static final String APP_ACTIVITY = "com.example.loginapp.LoginActivity";

    protected static final AppiumBy USERNAME   = (AppiumBy) AppiumBy.id(APP_PACKAGE + ":id/username");
    protected static final AppiumBy PASSWORD   = (AppiumBy) AppiumBy.id(APP_PACKAGE + ":id/password");
    protected static final AppiumBy LOGIN_BTN  = (AppiumBy) AppiumBy.id(APP_PACKAGE + ":id/loginButton");
    protected static final AppiumBy CANCEL_BTN = (AppiumBy) AppiumBy.id(APP_PACKAGE + ":id/cancelButton");
    protected static final AppiumBy WELCOME    = (AppiumBy) AppiumBy.id(APP_PACKAGE + ":id/welcomeText");
    protected static final AppiumBy ERROR      = (AppiumBy) AppiumBy.id(APP_PACKAGE + ":id/errorText");

    protected AndroidDriver driver;
    protected WebDriverWait wait;

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

    protected void enterCredentials(String user, String pass) {
        WebElement u = driver.findElement(USERNAME);
        u.clear();
        u.sendKeys(user);
        WebElement p = driver.findElement(PASSWORD);
        p.clear();
        p.sendKeys(pass);
        driver.hideKeyboard();
    }
}
