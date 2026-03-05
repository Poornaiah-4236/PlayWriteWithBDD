package com.poorna.Pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.options.LoadState;
import com.poorna.Utils.TestDataReader;

public class LoginPage {
	private Page page;

	public LoginPage(Page page) {
		this.page = page;
	}

	private String username = "#username";
	private String password = "#password";
	private String loginBtn = "input[value='Login']";
	private String tab = "a[href='#tab1default']";
	private String homePage = "img[src='images/top.png']";
	private String searchBox = "#twotabsearchtextbox";

	public void navigate() {
		String url = TestDataReader.getInstance().getProperty("baseUrl");
		System.out.println("Navigating to URL: " + url);		
		page.navigate(url);
		page.waitForLoadState(LoadState.LOAD);
		page.waitForSelector(searchBox);
		 page.onRequest(request -> {
	            System.out.println("Request URL: " + request.url());
	        });
		
		//System.out.println("Request URL: " + request.url());
	}

	public void login(String user, String pass) {
		page.click(tab);
		page.fill(username, user);
		page.fill(password, pass);
		page.click(loginBtn);
		page.locator(homePage).isVisible();
	}
}
