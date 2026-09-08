package login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** T2 — invalid / empty credentials are rejected on the login screen. */
class T2InvalidLoginTest extends LoginTestBase {

    @Test
    @DisplayName("T2a: wrong password -> error, stays on login screen")
    void wrongPassword() {
        enterCredentials("testuser", "wrong-password");
        driver.findElement(LOGIN_BTN).click();

        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR));
        assertTrue(error.isDisplayed(), "Error message should be shown");
        assertTrue(driver.findElement(USERNAME).isDisplayed(), "Should remain on login screen");
    }

    @Test
    @DisplayName("T2b: empty fields -> login disabled or rejected")
    void emptyFields() {
        WebElement loginBtn = driver.findElement(LOGIN_BTN);
        if (loginBtn.isEnabled()) {
            loginBtn.click();
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR));
            assertTrue(error.isDisplayed(), "Empty submit should be rejected with an error");
        } else {
            assertFalse(loginBtn.isEnabled(), "Login button should be disabled with empty fields");
        }
    }
}
