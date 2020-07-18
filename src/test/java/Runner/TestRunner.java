package Runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        //Specify feature file destinations
        features = "src\\test\\java\\Features",
        //Specify stepDefination code locations
        glue = "StepDefinitions",
        //
        monochrome = true,
        //Tags help grouping specific related scenarios
        tags = {},
        //Report generations
        plugin = {"pretty",
                "html:target\\reports\\cucumber",
                "json:target\\reports\\cucumber.json"
        }
)

public class TestRunner {

    //Runner Main

}