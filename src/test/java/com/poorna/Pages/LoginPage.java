package com.poorna.Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.poorna.Utils.ConfigReader;

public class LoginPage {
	private final Page page;
	private final Locator usernameInput;
	private final Locator passwordInput;
	private final Locator submitButton;
	private final Locator successMessage;
	private final Locator errorMessage;
	private final Locator logoutLink;

	public LoginPage(Page page) {
		this.page = page;
		this.usernameInput = page.locator("#username");
		this.passwordInput = page.locator("#password");
		this.submitButton = page.locator("#submit");
		this.successMessage = page.locator(".post-title");
		this.errorMessage = page.locator("#error");
		this.logoutLink = page.getByText("Log out");
	}

	public void navigate() {
		String url = ConfigReader.getInstance().getProperty("login.baseUrl");
		page.navigate(url);
		page.waitForLoadState(LoadState.LOAD);
	}

	public void login(String user, String pass) {
		usernameInput.fill(user);
		passwordInput.fill(pass);
		submitButton.click();
	}

	public Locator getSuccessMessage() {
		return successMessage;
	}

	public Locator getErrorMessage() {
		return errorMessage;
	}

	public Locator getLogoutLink() {
		return logoutLink;
	}
}
