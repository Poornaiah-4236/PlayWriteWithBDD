package com.poorna.stepdefinitions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.poorna.Pages.LoginPage;
import com.poorna.base.PlaywrightManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

	private LoginPage loginPage() {
		return new LoginPage(PlaywrightManager.getPage());
	}

	@Given("I am on the login page")
	public void iAmOnTheLoginPage() {
		loginPage().navigate();
	}

	@When("I log in with username {string} and password {string}")
	public void iLogInWithUsernameAndPassword(String user, String pass) {
		loginPage().login(user, pass);
	}

	@Then("I should see the {string} message")
	public void iShouldSeeTheMessage(String expectedMessage) {
		assertThat(loginPage().getSuccessMessage()).containsText(expectedMessage);
	}

	@Then("I should see a logout link")
	public void iShouldSeeALogoutLink() {
		assertThat(loginPage().getLogoutLink()).isVisible();
	}

	@Then("I should see an error message {string}")
	public void iShouldSeeAnErrorMessage(String expectedMessage) {
		assertThat(loginPage().getErrorMessage()).hasText(expectedMessage);
	}
}
