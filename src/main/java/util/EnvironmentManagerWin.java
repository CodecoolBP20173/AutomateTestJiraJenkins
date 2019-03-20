package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class EnvironmentManagerWin {
    private static String driverPath = System.getenv("driverPath");
    private static WebDriver driver;
    private static String nodeUrl = System.getenv("nodeUrl");


    public static void initChromeWebDriver() {

        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();

        RunEnvironment.setWebDriver(driver);
    }

    public static void initChromeWebDriverFromNode() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setBrowserName("chrome");

        try {
            driver = new RemoteWebDriver(new URL(nodeUrl), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        RunEnvironment.setWebDriver(driver);
    }

    public static void initFireFoxWebDriver() {
        System.setProperty("webdriver.gecko.driver", driverPath );
        driver = new FirefoxDriver();

        RunEnvironment.setWebDriver(driver);
    }

    public static void initFireFoxWebDriverFromNode() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setBrowserName("firefox");

        try {
            driver = new RemoteWebDriver(new URL(nodeUrl), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        RunEnvironment.setWebDriver(driver);
    }

    public static void shutDownDriver() {
        if(driver!=null) {
            System.out.println("Closing browser");
            RunEnvironment.getWebDriver().quit();
        }

    }
}
