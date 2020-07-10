package stepDefinitions;

import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.Driver;
import utils.PayloadUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class NumberOfStoriesAndGotSteps {

    WebDriver driver = Driver.getDriver();
    LoginPage loginPage = new LoginPage(driver);
    HomePage homePage = new HomePage(driver);
    int expectedTotalStories;
    GotPojo parspedResponse;

    @When("the user sends get request to get total number of issues assigned to assignee on API")
    public void the_user_sends_get_request_to_get_total_number_of_issues_assigned_to_assignee_on_API() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("rest/api/2/search").setCustomQuery("jql=assignee=Memis");

        HttpGet get = new HttpGet(uriBuilder.build());
        get.setHeader("Content-Type", "application/json");
        get.setHeader("Accept", "application/json");
        get.setHeader("Cookie", PayloadUtil.getJiraAutorization());
        HttpResponse response = client.execute(get);

        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AssigneePojo assigneePojo = objectMapper.readValue(response.getEntity().getContent(), AssigneePojo.class);
        expectedTotalStories = assigneePojo.getTotal();
        System.out.println("from api "+expectedTotalStories);

    }

    @Then("the user validates total number of stories for same assignee on IU")
    public void the_user_validates_total_number_of_stories_for_same_assignee_on_IU() throws InterruptedException {
       loginPage.login(driver,ConfigReader.getProperty("verifyUrl"));
        homePage.assigneeSearchField.sendKeys("Memis");
        int actualUITotalIssues = homePage.assigneeIssueList.size();
        System.out.println(actualUITotalIssues);
        Assert.assertEquals(expectedTotalStories, actualUITotalIssues);

    }

    @When("user sends a get request on APi to {string}")
    public void user_sends_a_get_request_on_APi_to(String url) {

        parspedResponse = given().header("accept", ContentType.JSON).when().get(url)
                .then().statusCode(200).and().contentType(ContentType.JSON).extract().as(new TypeRef<GotPojo>() {
                });
    }

    @Then("the user get all houses and ids and store them in a map")
    public void the_user_get_all_houses_and_ids_and_store_them_in_a_map() {
        Map<String, String> gotProject = new HashMap<>();
        for (int i = 0; i < parspedResponse.getData().size(); i++) {
            String gotId = parspedResponse.getData().get(i).get_id();
            String gotHouse = parspedResponse.getData().get(i).getHouse();
            gotProject.put(gotId, gotHouse);

        }
        for( String id: gotProject.keySet()){
            System.out.println(id+": "+gotProject.get(id));
        }
    }
}
