package pageFactory;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ComponentPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private NavigateToPages navigateToPages;

    private static final int TIMEOUT = 10;

    @FindBy(xpath = ".//form[@id='components-add__component']/div[@class='components-add__confirm']/button")
    private WebElement addComponentButton;

    @FindBy(xpath = ".//form[@id='components-add__component']/div[@class='components-add__name']/input")
    private WebElement componentNameInput;

    @FindBy(xpath = ".//form[@id='components-add__component']/div[@class='components-add__assignee']/*/input")
    private WebElement componentAssigneeInput;

    @FindBys(@FindBy(xpath = ".//table[@id='components-table']/tbody[@class='items']/tr"))
    private List<WebElement> componentTableRows;

    private By componentTablePath = By.xpath(".//table[@id='components-table']/tbody[@class='items']");
    private By componentTableRowsPath = By.xpath(".//table[@id='components-table']/tbody[@class='items']/tr");
    private By componentNamePathFromRow = By.xpath(".//td[@class='components-table__name']/div/a");
    private By dynamicTableMenuPathFromRow = By.xpath(".//td[@class='dynamic-table__actions']/div/a");

    private By deleteButtonPath = By.linkText("Delete");

    private By submitButtonOnDeleteForm = By.xpath("//input[@id='submit']");

    public ComponentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        wait = new WebDriverWait(this.driver, TIMEOUT);//, POLLING);
        navigateToPages = new NavigateToPages(this.driver);
    }

    public void setComponentName(String componentName) {
        wait.until(ExpectedConditions.visibilityOf(componentNameInput));
        componentNameInput.sendKeys(componentName);
        wait.until(ExpectedConditions.textToBePresentInElementValue(componentNameInput, componentName));
    }

    public String getTextFromComponentNameInput() {
        return componentNameInput.getAttribute("value");
    }

    public void setAssigneeInput(String assignee) {
        wait.until(ExpectedConditions.elementToBeClickable(componentAssigneeInput));
        componentAssigneeInput.click();
        componentAssigneeInput.sendKeys(assignee);

        wait.until(ExpectedConditions.textToBePresentInElementValue(componentAssigneeInput, assignee));
        componentAssigneeInput.sendKeys(Keys.ENTER);
    }

    public String getAssigneeInputText() {
        return componentAssigneeInput.getAttribute("value");
    }

    public void clickOnAddComponent() {
        wait.until(ExpectedConditions.elementToBeClickable(addComponentButton));
        addComponentButton.click();
    }

    public boolean isComponentExist(String projectName) {

        try {
            for (WebElement row : componentTableRows) {
                if (row.findElement(componentNamePathFromRow).getText().equals(projectName))
                    return true;
            }
        } catch (WebDriverException exception) {
            return false;
        }
        return false;
    }

    public void removeProject(String projectName) {
        //wait until all rows are in item-state-ready, after adding itsstate is item-state-successful
        for (WebElement row : componentTableRows) {
            wait.until(ExpectedConditions.attributeContains(row, "class", "item-state-ready"));
        }


        for (WebElement row : componentTableRows) {
            if (row.findElement(componentNamePathFromRow).getText().equals(projectName)) {
                WebElement dynamicTableMenu = row.findElement(dynamicTableMenuPathFromRow);
                dynamicTableMenu.click();

                WebElement deleteButton = wait.until(ExpectedConditions.presenceOfElementLocated(deleteButtonPath));
                deleteButton.click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(submitButtonOnDeleteForm)).click();
            }
        }
    }

    public void waitForPageLoadComplete(WebDriver driver, int specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, specifiedTimeout);
        wait.until(driver1 -> String
                .valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState"))
                .equals("complete"));
    }

}
