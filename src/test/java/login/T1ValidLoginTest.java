package login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** T1 — valid credentials log the user in. */
class T1ValidLoginTest extends LoginTestBase {

    @Test
    @DisplayName("T1: valid credentials -> welcome screen")
    void validLogin() {
        enterCredentials("testuser", "Passw0rd!");
        driver.findElement(LOGIN_BTN).click();

        WebElement welcome = wait.until(ExpectedConditions.visibilityOfElementLocated(WELCOME));
        assertTrue(welcome.isDisplayed(), "Welcome view should be shown after valid login");
    }
}
