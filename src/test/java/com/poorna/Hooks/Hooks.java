package com.poorna.Hooks;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotType;
import com.poorna.Utils.ConfigReader;
import com.poorna.base.PlaywrightManager;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	@Before
	public void setup() {
		String browser = ConfigReader.getInstance().getProperty("browser");
		boolean headless = Boolean.parseBoolean(ConfigReader.getInstance().getProperty("headless"));
		int slowMo = Integer.parseInt(ConfigReader.getInstance().getProperty("slowMo"));
		PlaywrightManager.initBrowser(browser, headless, slowMo);
	}

	@AfterStep
	public void afterStep(Scenario scenario) {
		if (scenario.isFailed()) {
			Page page = PlaywrightManager.getPage();
			if (page != null) {
				byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setType(ScreenshotType.PNG));
				scenario.attach(screenshot, "image/png", scenario.getName());
			}
		}
	}

	@After
	public void tearDown() {
		PlaywrightManager.closeBrowser();
	}
}
