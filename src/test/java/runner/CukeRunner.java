package runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src\\test\\resources\\jiraAndGot\\JiraApiAndUi.feature","src\\test\\resources\\jiraAndGot\\NumberOfStoriesAndGot.feature"},
        glue = {"src\\test\\java\\stepDefinitions\\JiraApiCall.java","src\\test\\java\\stepDefinitions\\NumberOfStoriesAndGotSteps.java"},
        monochrome = false,
        dryRun = false

)
public class CukeRunner {

}
