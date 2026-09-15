package com.poorna.Pages;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.poorna.Utils.ConfigReader;

public class AmazonPage {
	private final Page page;
	private final Locator searchBox;
	private final Locator searchIcon;
	private final Locator productTitle;
	private final Locator noResultsMessage;

	public AmazonPage(Page page) {
		this.page = page;
		this.searchBox = page.locator("#twotabsearchtextbox");
		this.searchIcon = page.locator("#nav-search-submit-button");
		this.productTitle = page.locator("xpath=//div[@data-cy='title-recipe']//h2");
		this.noResultsMessage = page.getByText(
				Pattern.compile("did not match any products|no results for", Pattern.CASE_INSENSITIVE));
	}

	public void navigate() {
		String url = ConfigReader.getInstance().getProperty("amazon.baseUrl");
		page.navigate(url);
		page.waitForLoadState(LoadState.LOAD);
	}

	public List<String> searchForProduct(String product) {
		searchBox.fill(product);
		searchIcon.click();
		page.waitForLoadState();
		return productTitle.allTextContents().stream()
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.collect(Collectors.toList());
	}

	public boolean hasNoResultsMessage() {
		return noResultsMessage.count() > 0;
	}
}
