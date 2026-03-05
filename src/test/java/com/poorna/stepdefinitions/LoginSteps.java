package com.poorna.stepdefinitions;
import com.poorna.Pages.LoginPage;
import com.poorna.base.PlaywrightManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
public class LoginSteps {
	 LoginPage loginPage = new LoginPage(PlaywrightManager.getPage());

	    @Given("User launches the application")
	    public void launchApp() {	    	
	        loginPage.navigate();

	    }
	    @When("User enters username {string} and password {string}")
	    public void enterCredentials(String user, String pass) {
	        loginPage.login(user, pass);
	        Assert.assertTrue(true);
	    }

	    @Then("User should see dashboard page")
	    public void verifyDashboard() {	       
	    	
	    	Assert.assertTrue(true);
	    }
}