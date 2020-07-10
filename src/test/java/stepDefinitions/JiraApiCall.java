package stepDefinitions;

import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.Driver;
import utils.PayloadUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class JiraApiCall {


    WebDriver driver= Driver.getDriver();
    LoginPage loginPage= new LoginPage(driver);
    HomePage homePage= new HomePage(driver);
    String expectedId;


    @When("the user uses Post method to generate a authorization cookie for Jira back end testing")
    public void the_user_uses_Post_method_to_generate_a_authorization_cookie_for_Jira_back_end_testing() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("rest/auth/1/session");

        HttpPost post = new HttpPost(uriBuilder.build());

        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");

        HttpEntity entity = new StringEntity(PayloadUtil.getCookieAuthPayload(ConfigReader.getProperty("jiraUserName"), ConfigReader.getProperty("jiraPassword")));
        post.setEntity(entity);

        HttpResponse response = client.execute(post);

        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        JiraPojo jiraPojo = objectMapper.readValue(response.getEntity().getContent(), JiraPojo.class);

//        sessionName = jiraPojo.getSession().get("name");
//        value = jiraPojo.getSession().get("value");
//
//        System.out.println(sessionName + " " + value);

    }

    @When("the user creates user stories via APi with {string}, {string}, {string}")
    public void the_user_creates_user_stories_via_APi_with(String summary, String description, String issueType) throws IOException, URISyntaxException {

        HttpClient client= HttpClientBuilder.create().build();
        URIBuilder uriBuilder= new URIBuilder();

        uriBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("rest/api/2/issue");

        HttpPost post = new HttpPost(uriBuilder.build());

        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");
        post.setHeader("Cookie",PayloadUtil.getJiraAutorization());

        HttpEntity entity= new StringEntity(PayloadUtil.jiraCreateIssuePayload(summary,description,issueType));
        post.setEntity(entity);

        HttpResponse response=client.execute(post);
        Assert.assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());

        ObjectMapper objectMapper= new ObjectMapper();

        Map<String, String> responsePars=objectMapper.readValue(response.getEntity().getContent(), new TypeReference<Map<String, String>>() {});

            expectedId =responsePars.get("key");
        }


    @Then("the user verifies on ui {string}, {string}, {string}")
    public void the_user_verifies_on_ui(String summary, String description, String issueType) throws InterruptedException {
        if(!driver.getCurrentUrl().contains(ConfigReader.getProperty("url"))) {
            loginPage.login(driver, ConfigReader.getProperty("url"));
        }

        Thread.sleep(3000);
      driver.navigate().refresh();
        driver.switchTo().frame(homePage.iframe);

        homePage.issueDetails.click();

        driver.switchTo().parentFrame();
        Assert.assertEquals(expectedId,homePage.actualID.getText().trim());
        Assert.assertEquals(summary,homePage.actualSummary.getText().trim());
        Assert.assertEquals(description,homePage.actualDescription.getText().trim());
        Assert.assertEquals(issueType,homePage.actualIssueType.getText().trim());
        driver.navigate().back();

    }
}