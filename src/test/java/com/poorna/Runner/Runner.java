package com.poorna.Runner;
import org.junit.runner.RunWith;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.junit.Cucumber;
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.poorna.stepdefinitions", "com.poorna.Hooks"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        dryRun = false,
        monochrome = true,
        tags = "@Amazon"
)
public class Runner extends AbstractTestNGCucumberTests{

}
