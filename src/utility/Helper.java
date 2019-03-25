package utility;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Helper {

	public static String getXpathForCustomField(WebDriver driver, String xPath, Boolean isText,Boolean isPickList) {
		String sfdcFieldId = driver.findElement(By.xpath(xPath)).getAttribute("for");
		if (isText) {
			return "//input[@id=\"" + sfdcFieldId + "\" and @type=\"text\"]";
		}
		if (isPickList) {
			return "//select[@id=\"" + sfdcFieldId + "\"]";
		}
		return null;
	}
	
	public static String replaceParametersInXPath(String xPath,String data){
		if(xPath.contains("<<>>")){
			return xPath.replace("<<>>", data);
		}
		return xPath;
	}
	
	public static String getCurrentDirectory(){
		try {
			File currentDirFile = new File(".");
			return currentDirFile.getCanonicalPath();
		} catch (IOException e) {
			System.out.println("Error occured while getting current directory location");
			e.printStackTrace();
		}
		
		return null;
		
		/*try {
			String currentDir = currentDirFile.getCanonicalPath();
			System.out.println("Current Directory path is "+ currentDir);
			String testTxtPath = currentDir + File.separatorChar + "test.txt";
			System.out.println("Looking for test.txt in this path: " + testTxtPath);
			FileInputStream testFile = new FileInputStream(testTxtPath);
			System.out.println(testFile.read());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/		
	}
	
	public static void waitForPageReadyStateComplete(final WebDriver driver, WebDriverWait wait){
/*		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver t) {
				System.out.println("Page title is " + driver.getTitle() + ", Checking for page load");
				String temp = String.valueOf(((JavascriptExecutor)driver).executeScript("return document.readyState"));
				return temp.equals("complete");
			}
		});*/
		
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        wait.until(pageLoadCondition);
	}	

}
