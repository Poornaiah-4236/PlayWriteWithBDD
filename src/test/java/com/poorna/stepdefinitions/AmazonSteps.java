package com.poorna.stepdefinitions;

import com.poorna.Pages.AmazonPage;
import com.poorna.base.PlaywrightManager;

import io.cucumber.java.en.When;

public class AmazonSteps{
	AmazonPage amazonPage;

	@When("User searches for product {string}")
	public void searchForProduct(String product) throws InterruptedException {
		amazonPage=new AmazonPage(PlaywrightManager.getPage());
		amazonPage.searchForProduct(product);
	}
}
