package pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProjectSettingsPage extends PageObject {
    private ComponentPage componentPage;
    private static final int TIMEOUT = 10;

    @FindBy(id = "administer_project_components")
    private WebElement componentsAdminMenuItem;

    public ProjectSettingsPage(WebDriver driver) {
        super(driver);
        componentPage = new ComponentPage(this.driver);
    }

    public void clickOnComponentsMenuItem() {
        wait.until(ExpectedConditions.elementToBeClickable(componentsAdminMenuItem)).click();
    }

    /** Component Form **/
    public void setComponentNameInput(String componentName) {
        componentPage.setComponentName(componentName);
    }

    public void setComponentAssigneeInput(String assignee) {
        componentPage.setAssigneeInput(assignee);
    }

    public String getTextFromComponentNameInput() {
        return componentPage.getTextFromComponentNameInput();
    }

    public String getAssigneeInputText() {
        return componentPage.getAssigneeInputText();
    }

    public void clickOnAddComponent() {
        componentPage.clickOnAddComponent();
    }

    public boolean isComponentExist(String componentName) {
        return componentPage.isComponentExist(componentName);
    }
}
