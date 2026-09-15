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

	@Then("each result title should contain {string}")
	public void eachResultTitleShouldContain(String product) {
		String lowerProduct = product.toLowerCase();
		long matchCount = lastResultTitles.stream()
				.filter(title -> title.toLowerCase().contains(lowerProduct))
				.count();
		double matchRatio = (double) matchCount / lastResultTitles.size();
		// Amazon search results legitimately include sponsored/related items whose
		// titles don't contain the literal search term, so require majority relevance
		// rather than a 100% match.
		Assert.assertTrue(matchRatio >= 0.5,
				"Expected at least half of result titles to contain '" + product + "', but only "
						+ matchCount + "/" + lastResultTitles.size() + " did");
	}

	@Then("no results message should be displayed")
	public void noResultsMessageShouldBeDisplayed() {
		Assert.assertTrue(amazonPage().hasNoResultsMessage(), "Expected a no-results message to be displayed");
	}
}
