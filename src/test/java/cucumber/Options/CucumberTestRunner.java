package cucumber.Options;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/*
    We need a TestRunner file to run the BDD framework
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features",
        glue={"stepDefinitions"}    // If we wish to run only a particular testcase we write a tag in feature file and mention the tag here
                                    // If we were to run all testcases except this one then we write tags="not @AddPlaceGetPlace"
)

//@RunWith(Cucumber.class) states that we need Junit to run this.
// @CucumberOptions will give location of feature file and stepDefinitions file

public class CucumberTestRunner {

    // Without writing any stepDefinitions, if we run this TestRunner, we will get an error
    // indicating the given, when and then does not have a relevant implementation written yet.

    // All the syntaxes required to construct the stepDefinitions.java will be given in the output console when we run TestRunner.java

    // We copy it and make changes accordingly and implement the stepDefinitions.java
}
