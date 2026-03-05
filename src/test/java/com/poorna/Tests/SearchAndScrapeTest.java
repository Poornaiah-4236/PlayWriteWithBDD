package com.poorna.Tests;

import java.util.List;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.poorna.base.PlaywrightManager;
import com.poorna.Utils.TestDataReader;
import com.poorna.Pages.AmazonPage;

public class SearchAndScrapeTest {

	private Page page;

	@BeforeClass
	public void setUp() {
		String browser = TestDataReader.getInstance().getProperty("browser");
		String headlessStr = TestDataReader.getInstance().getProperty("headless");
		String slowMoStr = TestDataReader.getInstance().getProperty("slowMo");
		boolean headless = false;
		int slowMo = 0;
		if (headlessStr != null) {
			headless = headlessStr.trim().equalsIgnoreCase("true");
		}
		if (slowMoStr != null) {
			try {
				slowMo = Integer.parseInt(slowMoStr.replaceAll("\"", ""));
			} catch (NumberFormatException e) {
				// ignore, leave default
			}
		}
		if (browser == null || browser.isEmpty()) {
			browser = "chromium";
		}
		PlaywrightManager.initBrowser(browser, headless, slowMo);
		page = PlaywrightManager.getPage();
	}

	@Test
//	public void searchAndExtractPrices() throws InterruptedException {
//		String url = "https://www.amazon.in/s?k=phones&rh=p_123%3A46655&dc&qid=1772685701&rnid=91049095031&ref=sr_nr_p_123_1&ds=v1%3ACK86SMcVveRRss77xZDDFKhzM%2Bp7tm442mrSDJBydu0";
//		AmazonPage amazon = new AmazonPage(page);
//		List<String> items = amazon.getProductNamesAndPrices(url);
//		System.out.println("Raw items found: " + items.size());
//		// Parse items into (title, price) pairs
//		List<SimpleEntry<String, Double>> products = new ArrayList<>();
//		for (String it : items) {
//			if (it == null || it.trim().isEmpty()) continue;
//			String[] parts = it.split(" - ", 2);
//			String title = parts.length > 0 ? parts[0].trim() : "";
//			String priceStr = parts.length > 1 ? parts[1].trim() : "";
//			// Clean price string: remove non-digit and non-dot characters (like currency symbols and commas)
//			String cleaned = priceStr.replaceAll("[^0-9.,]", "");
//			// Replace commas with nothing; some prices use commas as thousand separators
//			cleaned = cleaned.replaceAll(",", "");
//			double price = 0.0;
//			try {
//				if (!cleaned.isEmpty()) {
//					price = Double.parseDouble(cleaned);
//				}
//			} catch (NumberFormatException e) {
//				System.out.println("Unable to parse price for item: '" + title + "' rawPrice='" + priceStr + "' cleaned='" + cleaned + "'");
//			}
//			products.add(new SimpleEntry<>(title, price));
//		}
//
//		// Sort by price descending
//		products.sort(Comparator.comparingDouble(SimpleEntry<String, Double>::getValue).reversed());
//
//		System.out.println("Products sorted by price (descending):");
//		for (SimpleEntry<String, Double> p : products) {
//			System.out.println(p.getKey() + " - " + p.getValue());
//		}
//	}

	@AfterClass
	public void tearDown() {
		PlaywrightManager.closeBrowser();
	}
}