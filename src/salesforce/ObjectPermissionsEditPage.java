package salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.Helper;

public class ObjectPermissionsEditPage {
	
	public static void navigateToObjPermissionsEditPage(WebDriver driver, String baseUrl,String profileId,WebDriverWait wait) {
		driver.get(baseUrl + "/" + profileId + "/e");
		Helper.waitForPageReadyStateComplete(driver, wait);
	}
	
	public static void setObjectReadPermission(WebDriver driver, String objId,Boolean permission,WebDriverWait wait) {
		WebElement checkBoxElem = driver.findElement(By.xpath("//input[contains(@id," + "'crudRead___" + objId + "')]" ));
		if(checkBoxElem.isSelected() == permission) {
			return;
		}else {
			checkBoxElem.click();
		}
	}	
	
	public static void setObjectEditPermission(WebDriver driver, String objId,Boolean permission,WebDriverWait wait) {
		WebElement checkBoxElem = driver.findElement(By.xpath("//input[contains(@id," + "'crudUpdate___" + objId + "')]" ));
		if(checkBoxElem.isSelected() == permission) {
			return;
		}else {
			checkBoxElem.click();
		}
	}
	
	public static void setObjectCreatePermission(WebDriver driver, String objId,Boolean permission,WebDriverWait wait) {
		WebElement checkBoxElem = driver.findElement(By.xpath("//input[contains(@id," + "'crudCreate___" + objId + "')]" ));
		if(checkBoxElem.isSelected() == permission) {
			return;
		}else {
			checkBoxElem.click();
		}
	}
	
	public static void setObjectDeletePermission(WebDriver driver, String objId,Boolean permission,WebDriverWait wait) {
		WebElement checkBoxElem = driver.findElement(By.xpath("//input[contains(@id," + "'crudDelete___" + objId + "')]" ));
		if(checkBoxElem.isSelected() == permission) {
			return;
		}else {
			checkBoxElem.click();
		}
	}
	
	public static void setObjectViewAllPermission(WebDriver driver, String objId,Boolean permission,WebDriverWait wait) {
		WebElement checkBoxElem = driver.findElement(By.xpath("//input[contains(@id," + "'crudViewAll___" + objId + "')]" ));
		if(checkBoxElem.isSelected() == permission) {
			return;
		}else {
			checkBoxElem.click();
		}
	}
	
	public static void setObjectModifyAllPermission(WebDriver driver, String objId,Boolean permission,WebDriverWait wait) {
		WebElement checkBoxElem = driver.findElement(By.xpath("//input[contains(@id," + "'crudModifyAll___" + objId + "')]" ));
		if(checkBoxElem.isSelected() == permission) {
			return;
		}else {
			checkBoxElem.click();
		}
	}	
	
	public static void clickSaveButton(WebDriver driver,WebDriverWait wait) {
		WebElement elem = driver.findElement(By.xpath("//input[@name='save' and @type = 'submit']"));
		elem.click();
		Helper.waitForPageReadyStateComplete(driver, wait);
	}	
	
	

}
