package pageFactory;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateIssueModal {

    WebDriver driver;
    WebDriverWait wait;
    WebElement popup;

    @FindBy(id="create-issue-submit")
    WebElement submit;

    @FindBy(id="qf-create-another")
    WebElement createAnotherCheckbox;

    private static By projectFieldLocator = By.id("project-field");
    private static By issueTypeFieldLocator = By.id("issuetype-field");
    private static By summaryFieldLocator = By.id("summary");
    private static By popupLinkLocator = By.xpath("//*[@id='aui-flag-container']//a[@class='issue-created-key issue-link']");
    private static By popupDivLocator = By.xpath("//*[@id='aui-flag-container']//descendant::div[contains(@class,'aui-message')]");

    public CreateIssueModal(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 10);
    }

    public void setProject(String project){
        WebElement projectField = wait.until(ExpectedConditions.elementToBeClickable(projectFieldLocator));
        projectField.sendKeys(project);
        projectField.sendKeys(Keys.ENTER);
    }

    public void setIssueType(String issueType){
        WebElement bugSelect = wait.until(ExpectedConditions.elementToBeClickable(issueTypeFieldLocator));
        bugSelect.sendKeys(issueType);
        bugSelect.sendKeys(Keys.RETURN);
    }

    public void setSummary(String summary){
        WebElement summaryField = wait.until(ExpectedConditions.elementToBeClickable(summaryFieldLocator));
        summaryField.sendKeys(summary);
    }

    public void setAssignee(){}

    public void setPriority(String priority){}

    public void addLabel(String label){}

    //clicks it and returns whether it is checked
    public boolean toggleCreateAnotherCheckbox(){
        createAnotherCheckbox.click();
        return createAnotherCheckbox.isSelected();
    }

    //returns the link's text in the popup, or empty string, if there were no popup
    public String submit(){
        submit.click();
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(popupDivLocator)).getText();
        }catch(ElementNotVisibleException e){
            return "";
        }
    }


}