package com.poorna.Hooks;
import com.poorna.base.PlaywrightManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
public class Hooks {
	 	@Before
	    public void setup() {
	        PlaywrightManager.initBrowser("chromium");
	    }

	    @After
	    public void tearDown() {
	        PlaywrightManager.closeBrowser();
	    }
}
