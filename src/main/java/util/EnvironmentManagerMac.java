package util;

import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class EnvironmentManagerMac {

    private static String driverPath = System.getenv("driverPath");
    private static WebDriver driver;
    //private static String nodeUrl = System.getenv("nodeUrl");
    private static String nodeUrl = System.getProperty("nodeUrl");


    public static void initChromeWebDriver() {

        driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", driverPath);
        RunEnvironment.setWebDriver(driver);
    }

    public static void initChromeWebDriverFromNode() {
        /*driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", driverPath);
        RunEnvironment.setWebDriver(driver);*/

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setBrowserName("chrome");
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        //capabilities.setPlatform(Platform.MAC);
        System.err.println(nodeUrl);

        try {
            driver = new RemoteWebDriver(new URL(nodeUrl), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        RunEnvironment.setWebDriver(driver);
    }

    public static void initFireFoxWebDriverFromNode() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setBrowserName("firefox");
        capabilities.setPlatform(Platform.MAC);

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
            driver.quit();
        }

        if(RunEnvironment.getWebDriver() != null) {
            System.out.println("Closing browser");
            RunEnvironment.getWebDriver().quit();
        }

    }
}
