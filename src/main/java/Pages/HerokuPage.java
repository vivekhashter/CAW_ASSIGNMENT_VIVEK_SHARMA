package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import driver.Driver;
import utils.SeleniumUtils;

public class HerokuPage {
	private static final By table_data = By.xpath("//summary[normalize-space()='Table Data']");
	private static final By Refresh_Button = By.xpath("//button[text()='Refresh Table']");

	public void clickonLogin() throws InterruptedException {

		SeleniumUtils.click(table_data, "table");
	}
	public void tableMatch() throws InterruptedException {

		SeleniumUtils.tableInsert();
	}

}
