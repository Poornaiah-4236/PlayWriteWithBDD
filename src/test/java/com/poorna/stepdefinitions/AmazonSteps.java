package com.poorna.stepdefinitions;

import java.util.List;

import org.testng.Assert;

import com.poorna.Pages.AmazonPage;
import com.poorna.base.PlaywrightManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AmazonSteps {

	private List<String> lastResultTitles;

	private AmazonPage amazonPage() {
		return new AmazonPage(PlaywrightManager.getPage());
	}

	@Given("I am on the Amazon home page")
	public void iAmOnTheAmazonHomePage() {
		amazonPage().navigate();
	}

	@When("I search for product {string}")
	public void iSearchForProduct(String product) {
		lastResultTitles = amazonPage().searchForProduct(product);
	}

	@Then("search results should be displayed")
	public void searchResultsShouldBeDisplayed() {
		Assert.assertFalse(lastResultTitles.isEmpty(), "Expected at least one search result");
	}

	@Then("no results message should be displayed")
	public void noResultsMessageShouldBeDisplayed() {
		Assert.assertTrue(amazonPage().hasNoResultsMessage(), "Expected a no-results message to be displayed");
	}
}
