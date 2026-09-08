package login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** T3 — Cancel button clears the form fields. */
class T3CancelTest extends LoginTestBase {

    @Test
    @DisplayName("T3: cancel clears username and password")
    void cancelClearsForm() {
        enterCredentials("somebody", "something");
        driver.findElement(CANCEL_BTN).click();

        assertEquals("", driver.findElement(USERNAME).getText().trim(), "Username should be cleared");
        assertEquals("", driver.findElement(PASSWORD).getText().trim(), "Password should be cleared");
    }
}
