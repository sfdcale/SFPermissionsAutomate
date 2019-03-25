package salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.Helper;

public class FLSEditPage {
	

	public static void navigateToFLSEditPage(WebDriver driver, String baseUrl,String profileId,String objId,WebDriverWait wait) {
		driver.get(baseUrl + "/setup/layout/flsedit.jsp?id=" + profileId + "&type=" + objId);
		Helper.waitForPageReadyStateComplete(driver, wait);
	}
	
	public static void setFieldReadAccess(WebDriver driver, String fieldId,Boolean permission,WebDriverWait wait) {
		WebElement checkBoxElem = driver.findElement(By.xpath("//input[contains(@id," + "'display_" + fieldId + "')]" ));
		if(checkBoxElem.isSelected() == permission) {
			return;
		}else {
			checkBoxElem.click();
		}
	}	
	
	public static void setFieldEditAccess(WebDriver driver, String fieldId,Boolean permission,WebDriverWait wait) {
		WebElement checkBoxElem = driver.findElement(By.xpath("//input[contains(@id," + "'edit_" + fieldId + "')]" ));
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
