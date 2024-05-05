package utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import driver.Driver;

public class SeleniumUtils {
	public SeleniumUtils() {
	}

	private static final By Refresh_Button = By.xpath("//button[text()='Refresh Table']");

	// method to wait until element is visible
	public static WebElement waitUntilElementToBeVisible(By by) {
		try {
			WebDriverWait wait = new WebDriverWait(Driver.driver, Duration.ofMinutes(2));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
//            ExtentLogger.fail
			return null;
		}
	}

	// method to wait until element is present
	public static WebElement waitUntilElementPresent(By by) {
		try {
			WebDriverWait wait = new WebDriverWait(Driver.driver, Duration.ofMinutes(2));
			return wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
//            ExtentLogger.fail
			return null;
		}
	}

	// method to wait until element is clickable
	public static WebElement waitUntilElementToBeClickable(By by) {
		try {
			WebDriverWait wait = new WebDriverWait(Driver.driver, Duration.ofMinutes(2));
			return wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
//            ExtentLogger.fail
			return null;
		}
	}

	// method to refresh page
	public static void refreshPage() {
		Driver.driver.navigate().refresh();
	}

	// method to wait until element is present and then click on the element
	public static void click(By by, String elementName) {
		WebDriverWait wait = new WebDriverWait(Driver.driver, Duration.ofSeconds(20));
		WebElement element = Driver.driver.findElement(by);
		try {
			element.click();
		} catch (Exception e) {
//            ExtentLogger.fail
		}
	}

	// method to wait until element is present and select value in dropdown
	public static void selectItemByVisibleText(By by, final String value) {
		waitUntilElementPresent(by);
		Select select = new Select(Driver.driver.findElement(by));
		select.selectByVisibleText(value);
	}

	// method to wait until element is present and then send value in field
	public static void sendKeys(By by, String value, String elementName) {
		try {
			WebElement element = Driver.driver.findElement(by);
			element.sendKeys(value);
		} catch (Exception e) {
//            ExtentLogger.fail
		}
	}

	private static String readJSONFromFile(String filePath) {
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
			return new String(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
			return null; // Return null or handle the error as appropriate
		}
	}

	public static void tableInsert() {
		String jsonData = readJSONFromFile("src/test/resources/Testdata/testdata.json");

		try {
			// Parse JSON array
			JSONArray jsonArray = new JSONArray(jsonData);

			// Find the input box by its ID or any other suitable locator
			WebElement inputBox = Driver.driver.findElement(By.xpath("//textarea[@id='jsondata']"));

			// Clear the input box
			inputBox.clear();
			inputBox.sendKeys("[");

			// Iterate through JSON objects
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				// Get name, age, and gender
				String name = jsonObject.getString("name");
				int age = jsonObject.getInt("age");
				String gender = jsonObject.getString("gender");
				String formattedData = String.format("{\"name\" : \"%s\", \"age\" : %d, \"gender\": \"%s\"}", name, age,
						gender);

				inputBox.sendKeys(formattedData);

				if (i < jsonArray.length() - 1) {
					inputBox.sendKeys(",\n");
				}
			}

			// End with closing square bracket
			inputBox.sendKeys("]");
			SeleniumUtils.click(Refresh_Button, "Refresh button");

			List<WebElement> nameElements = Driver.driver.findElements(By.xpath("//table//td[1]"));
			List<WebElement> ageElements = Driver.driver.findElements(By.xpath("//table//td[2]"));
			List<WebElement> genderElements = Driver.driver.findElements(By.xpath("//table//td[3]"));

			// Verify that the inserted data matches the data displayed on the UI
			boolean verificationSuccessful = true;

			for (int i = 0; i < jsonArray.length(); i++) {
				String nameFromUI = nameElements.get(i).getText();
				int ageFromUI = Integer.parseInt(ageElements.get(i).getText());
				String genderFromUI = genderElements.get(i).getText();

				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String name = jsonObject.getString("name");
				int age = jsonObject.getInt("age");
				String gender = jsonObject.getString("gender");

				if (!nameFromUI.equals(name) || ageFromUI != age || !genderFromUI.equals(gender)) {
					verificationSuccessful = false;
					break;
				}
			}

			if (verificationSuccessful) {
				System.out.println("Data verification successful!");
			} else {
				System.out.println("Data verification failed!");
			}

		} catch (JSONException e) {
			e.printStackTrace();

		}

	}
}
