package test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import pageFactory.CreateIssue;
import javafx.stage.Popup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageFactory.Browse_Issues;
import pageFactory.CreateIssueModal;
import pageFactory.Login;
import util.RunEnvironment;
import util.Utils;

public class TestCreateIssue {

    Login login;
    CreateIssue createIssue;
    WebDriver driver;

    @BeforeEach
    public void setup() {
        Utils.setup();
        driver = RunEnvironment.getWebDriver();
        login = new Login(driver);
        createIssue = new CreateIssue(driver);
        driver.manage().window().maximize();

    }

    @DisplayName("Test create issue with exist project")
    @Test
    public void testCreateAnIssueByButton() {
        login.login();
        createIssue.createAnIssueByButton("Toucan", "Task", "KRK");
        Assertions.assertTrue(createIssue.isIssueCreateSuccessfully(), "Confirm issue is successfully created.");
    }

    @DisplayName("Test create all combination: Available Projects: TOUCAN, JETI, COALA" +
                 "Expected Issue Types: Story, Task, Bug, Sub-task")
    @Test
    public void testCreateIssueAllCombine() {
        login.login();

    }


    @AfterEach
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCreateIssue {
    private WebDriver driver;
    private Login login;

    @BeforeAll
    void initAll() {
        Utils.setup();
        driver = RunEnvironment.getWebDriver();
        driver.manage().window().maximize();
        login = new Login(driver);

    }

    @BeforeEach
    void beforeEach() {
        login.login();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1)
    void createIssue(String project, String issueType, String summary) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("create_link"))).click();
        CreateIssueModal modal = new CreateIssueModal(driver);
        modal.setProject(project);
        modal.setIssueType(issueType);
        modal.setSummary(summary);
        CreateIssueModal.PopupMessage popupMessage = modal.submit();
        System.out.println(popupMessage.message);
        assertTrue(popupMessage.message.contains(project));
        assertTrue(popupMessage.message.contains("successfully created"));
        deleteIssue(popupMessage.urlOfCreatedIssue);
    }

    void deleteIssue(String url) {
        driver.get(url);
        Browse_Issues browse = new Browse_Issues(driver);
        browse.deleteSelectedIssue();
    }

    @AfterAll
    public void tearDown() {
        Utils.tearDown();
    }
}
