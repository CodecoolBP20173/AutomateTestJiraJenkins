package pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Utils;

public class NavigateToPages {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;

    @FindBy(id = "browse_link")
    private WebElement projectsMenuItem;

    @FindBy(id = "project_view_all_link_lnk")
    private WebElement viewAllProjects;

    @FindBy(xpath = "//div[contains(@class,'projects-sidebar')]//div[contains(@class,'aui-sidebar-footer')]" +
            "//a[contains(@href, '/plugins/servlet/project-config/')]")
    private WebElement projectSettingsMenuItem;

    @FindBy(xpath = "//div[contains(@class,'projects-sidebar')]//div[contains(@class,'aui-sidebar-body')]" +
            "//a[contains(@href, 'components-page')]")
    private WebElement componentsSideMenuItem;

    @FindBy(xpath = "//a[@data-link-id='com.codecanvas.glass:glass']")
    private WebElement glassDocMenuItem;


    public NavigateToPages(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        wait = new WebDriverWait(this.driver, TIMEOUT);//, POLLING);
    }

    public void goToTheProject(String projectName) {
        wait.until(ExpectedConditions.elementToBeClickable(projectsMenuItem));
        projectsMenuItem.click();

        wait.until(ExpectedConditions.elementToBeClickable(viewAllProjects));
        viewAllProjects.click();

        WebElement project = this.driver.findElement(By.xpath(getProjectXPath(projectName)));
        wait.until(ExpectedConditions.elementToBeClickable(project));
        project.click();
    }

    public void goToProjectSettingsPage(String projectName) {
        goToTheProject(projectName);

        wait.until(ExpectedConditions.elementToBeClickable(projectSettingsMenuItem));
        projectSettingsMenuItem.click();
    }

    public void goToComponentsPageWithSideBar() {
        wait.until(ExpectedConditions.elementToBeClickable(componentsSideMenuItem)).click();
    }

    public void clickOnGlassDocumentNavItem() {
        wait.until(ExpectedConditions.elementToBeClickable(glassDocMenuItem)).click();
        Utils.acceptAlert(this.driver);
    }

    private String getProjectXPath(String projectName) {
        return ".//*[@original-title = '" + projectName + "']";
    }

}
