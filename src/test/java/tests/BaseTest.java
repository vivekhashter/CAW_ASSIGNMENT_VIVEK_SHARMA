package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import Pages.HerokuPage;
import config.ConfigFactory;
import driver.Driver;

public class BaseTest {
	HerokuPage hp = new HerokuPage();

	protected BaseTest() {
	}
    @BeforeMethod
	public void setUp() throws InterruptedException {
		Driver.initDriver();
		
	}

}
