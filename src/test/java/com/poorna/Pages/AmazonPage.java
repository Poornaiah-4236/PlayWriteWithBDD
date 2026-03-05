package com.poorna.Pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.poorna.Utils.TestDataReader;

public class AmazonPage {
	private Page page;
	private String searchBox = "#twotabsearchtextbox";
	private String probucct = "input[type='submit']";
	private String searchIcon = "#nav-search-submit-button";
	private String Samsung = "xpath=//span[text()='Samsung' or contains(., 'Samsung')]/ancestor::a//i|//span[text()='Samsung']/ancestor::li[@class='a-icon a-icon-checkbox']";
	private String Price = "css=.s-main-slot .s-result-item .a-price-whole";
	private String productTitle = "xpath=//div[@data-cy='title-recipe']//h2";

	public AmazonPage(Page page) {
		this.page = page;
		System.out.println("Navigating to Amazon");
	}

	public void navigate() {
		String url = TestDataReader.getInstance().getProperty("baseUrl");
		System.out.println("Navigating to URL: " + url);
		page.navigate(url);
		page.waitForLoadState(LoadState.LOAD);
		// page.waitForSelector(searchBox);

	}

	public void searchForProduct(String product) throws InterruptedException {
		page.locator(searchBox).fill(product);		
		page.locator(searchIcon).click();
		page.waitForLoadState();
		Thread.sleep(2000);
		if (page.locator(Samsung).count() > 0) {
			System.out.println("Samsung filter is visible");
			page.locator(Samsung).first().click();
			Thread.sleep(2000);
		}
		try {
//			Locator priceLocators = page.locator(Price);
//			int count = priceLocators.count();
//			System.out.println("Found price elements: " + count);
//			List<String> prices = priceLocators.allTextContents()
//					.stream().map(String::trim)
//					.filter(s -> !s.isEmpty())
//					.map(s->s.replace(",", ""))
//					.collect(Collectors.toList());
//			System.out.println("Extracted prices:");
//			for (String p : prices) {
//				System.out.println(p);
//			}
			Locator titleLocators = page.locator(productTitle);
			List<String> titles = titleLocators.allTextContents()
					.stream().map(String::trim)
					.filter(s -> !s.isEmpty())
					.collect(Collectors.toList());
		//	System.out.println("Extracted titles:"+titles.size()+" items"+titles);
			for (String t : titles) {
				System.out.println("Product title : "+t);
			}
			List<Map<String, String>> products = titleLocators.allTextContents()
					.stream().map(String::trim)
					.filter(s -> !s.isEmpty())
					.map(title -> {
						String price = "";
						try {
							price = page.locator("xpath=//div[@data-cy='title-recipe']//h2[contains(text(),'" + title + "')]/ancestor::div[@data-cy='title-recipe']//span[@class='a-price-whole']").first().textContent().trim();
						} catch (Exception e) {
							System.out.println("Price not found for product: " + title);
						}
						Map<String, String> productInfo = new HashMap<>();
						productInfo.put("title", title);
						productInfo.put("price", price);
						return productInfo;
					})
					.collect(Collectors.toList());
		} catch (Exception e) {
			System.out.println("Error extracting prices: " + e.getMessage());
		}

	}
}
