package com.poorna.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightManager {
	private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
	private static ThreadLocal<Browser> browser = new ThreadLocal<>();
	private static ThreadLocal<Page> page = new ThreadLocal<>();

	// Backward-compatible init: defaults to non-headless and 0 slowMo
	public static void initBrowser(String browserName) {
		initBrowser(browserName, false, 0);
	}

	public static void initBrowser(String browserName, boolean headless, int slowMo) {

		playwright.set(Playwright.create());

		List<String> args = new ArrayList<>();
		if (!headless) {
			args.add("--start-maximized");
		}

		switch (browserName) {
		case "firefox":
			browser.set(playwright.get().firefox()
				.launch(new BrowserType.LaunchOptions()
					.setHeadless(headless)
					.setArgs(args)
					.setSlowMo((double) slowMo)));
			break;
		case "webkit":
			browser.set(playwright.get().webkit()
				.launch(new BrowserType.LaunchOptions()
					.setHeadless(headless)
					.setArgs(args)
					.setSlowMo((double) slowMo)));
			break;
		case "chromium":
			BrowserType.LaunchOptions chromiumOptions = new BrowserType.LaunchOptions()
				.setHeadless(headless)
				.setArgs(args)
				.setSlowMo((double) slowMo);
			browser.set(playwright.get().chromium().launch(chromiumOptions));
			break;
		default:
			// fallback to chromium if unknown
			browser.set(playwright.get().chromium()
				.launch(new BrowserType.LaunchOptions()
					.setHeadless(headless)
					.setArgs(args)
					.setSlowMo((double) slowMo)));
		}
		// Create a context with no viewport to allow the browser to open maximized when not headless
		BrowserContext context = browser.get().newContext(new Browser.NewContextOptions().setViewportSize(null));
		page.set(context.newPage());
	}

	public static Page getPage() {
		return page.get();
	}

	public static void closeBrowser() {
		try {
			if (page.get() != null) {
				page.get().close();
			}
		} catch (Exception e) {
			// swallow - closing best-effort
		}
		try {
			if (browser.get() != null) {
				browser.get().close();
			}
		} catch (Exception e) {
			// swallow
		}
		try {
			if (playwright.get() != null) {
				playwright.get().close();
			}
		} catch (Exception e) {
			// swallow
		}
		// remove ThreadLocal values to avoid leaks in long-running test runners
		page.remove();
		browser.remove();
		playwright.remove();
	}
}