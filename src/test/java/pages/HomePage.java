package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {
    public HomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }


    @FindBy(id = "key-val")
    public WebElement actualID;

    @FindBy(xpath = "//div[@class='user-content-block']")
    public WebElement actualDescription;

    @FindBy(id = "type-val")
    public WebElement actualIssueType;

    @FindBy(id = "summary-val")
    public WebElement actualSummary;

    @FindBy(id = "gadget-10003")
    public WebElement iframe;

    @FindBy(xpath = "(//div[@class='activity-item-summary']//a[contains(@href,'/browse/')])[1]")
    public WebElement issueDetails;

    @FindBy(xpath = "//input[@id='ghx-backlog-search-input']")
    public WebElement assigneeSearchField;

    @FindBy(xpath = "//div[@class='ghx-issues js-issue-list ghx-has-issues']//img[@data-tooltip='Assignee: Memis']")
    public List<WebElement> assigneeIssueList;





}
