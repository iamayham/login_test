# Login Form – Appium Test Automation

UI test automation for a sample Android Login Form (Username, Password, Login, Cancel)
using **Java + JUnit 5 + Appium (UiAutomator2)**.

## Tests

| Class | Covers |
|-------|--------|
| `T1ValidLoginTest`   | Valid credentials → welcome screen |
| `T2InvalidLoginTest` | Wrong password → error; empty fields → login disabled/rejected |
| `T3CancelTest`       | Cancel button clears the form |

`LoginTestBase` holds the shared driver setup, locators, and `enterCredentials` helper.

**Author:** Ayham Kalsam

## Prerequisites

- JDK 17+, Maven
- Node + Appium 2: `npm i -g appium && appium driver install uiautomator2`
- Android SDK + an emulator or a USB device with USB debugging on
- The app under test installed on the device (or set an APK path, see below)

## Configure

Edit the constants at the top of `LoginTestBase.java` to match your app:

- `APP_PACKAGE`, `APP_ACTIVITY`
- Element resource-ids: `username`, `password`, `loginButton`, `cancelButton`, `welcomeText`, `errorText`

Use **Appium Inspector** to read the real ids. To install a fresh build each run,
uncomment `options.setApp("/absolute/path/to/app-debug.apk")`.

## Run

```bash
appium                                   # terminal 1: start server on 127.0.0.1:4723
# start an emulator / connect a device

mvn test                                 # all tests
mvn test -Dtest=T1ValidLoginTest         # one class
```
