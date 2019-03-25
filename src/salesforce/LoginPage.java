package salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.Helper;

public class LoginPage {
	
	public static String usernameInputTextXPath = "//*[@id='username']";
	public static String passwordInputTextXPath = "//*[@id='password']";
	public static String loginButtonXPath = "//*[@id='Login']";	

	public static void enterUserName(WebDriver driver,String uname) {
		WebElement elem = driver.findElement(By.xpath(usernameInputTextXPath));
		elem.sendKeys(uname);
	}
	
	public static void enterPassword(WebDriver driver,String pwd) {
		WebElement elem = driver.findElement(By.xpath(passwordInputTextXPath));
		elem.sendKeys(pwd);
	}
	
	public static void clickOnLoginButton(WebDriver driver,WebDriverWait wait) {
		WebElement elem = driver.findElement(By.xpath(loginButtonXPath));
		elem.click();
		Helper.waitForPageReadyStateComplete(driver, wait);		
	}
	
	public static void navigateToLoginPage(WebDriver driver, String loginUrl,WebDriverWait wait) {
		driver.get(loginUrl);
		Helper.waitForPageReadyStateComplete(driver, wait);
	}

}
