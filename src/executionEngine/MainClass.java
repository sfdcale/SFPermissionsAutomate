package executionEngine;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import salesforce.FLSEditPage;
import salesforce.LoginPage;
import salesforce.ObjectPermissionsEditPage;
import utility.ExcelUtils;
import utility.Helper;

public class MainClass {
	
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static String settingsSheetName = "Settings";
	public static String objPermissionsSheetName = "ObjectPermissions";
	public static String fieldPermissionsSheetName = "FieldPermissions";
	public static String baseUrl;
	public static String currentDir;
	public static String chromeDriverPath;
	
	public static void main(String[] args) {
		
		setExcelConfigPath();
		setChromeDriverPathProperty();
		instantiateWebDriver();
		loginToSalesforce();
		setBaseUrl();
		
		String operationType = ExcelUtils.getCellData(5, 1, settingsSheetName);
		
		if(operationType.equalsIgnoreCase("") || (!operationType.equalsIgnoreCase("objectpermissions") && !operationType.equalsIgnoreCase("fieldpermissions"))) {
			System.out.println("OperationType is not properly set. Aborting..");
			System.exit(1);
		}
		
		if(operationType.equalsIgnoreCase("ObjectPermissions")) {
			setObjectPermissions();
		}
		
		if(operationType.equalsIgnoreCase("FieldPermissions")) {
			setFieldPermissions();
		}
		
	}
	
	public static void loginToSalesforce() {
		String loginUrl = ExcelUtils.getCellData(2, 1, settingsSheetName);
		System.out.println("loginUrl is: " + loginUrl);
		if(loginUrl.equalsIgnoreCase("")) {
			System.out.println("LoginUrl is not set. Aborting..");
			System.exit(1);
		}
		LoginPage.navigateToLoginPage(driver, loginUrl, wait);
		
		String uname = ExcelUtils.getCellData(3, 1, settingsSheetName);
		if(uname.equalsIgnoreCase("")) {
			System.out.println("uname is not set. Aborting..");
			System.exit(1);
		}
		LoginPage.enterUserName(driver, uname);
		
		String pwd = ExcelUtils.getCellData(4, 1, settingsSheetName);
		if(pwd.equalsIgnoreCase("")) {
			System.out.println("pwd is not set. Aborting..");
			System.exit(1);
		}
		LoginPage.enterPassword(driver, pwd);
		
		LoginPage.clickOnLoginButton(driver,wait);		
	}
	
	public static void instantiateWebDriver() {
		try {
			driver = new ChromeDriver();
			driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			
			wait = new WebDriverWait(driver, 180);			
		}catch(Exception e) {
			System.out.println("Exception occured instantiating driver. Aborting..");
			e.printStackTrace();
			System.exit(1);
		}

	}
	
	public static void setExcelConfigPath() {
		String currentDir = Helper.getCurrentDirectory();
		System.out.println("Current Directory path is " + currentDir);
		if(currentDir == null) {
			System.out.println("current Directory is null. Aborting.");
			System.exit(1);
		}		
		String configFilePath = currentDir + File.separatorChar + "config.xlsx";
		System.out.println("Going to look for config xls in path: " + configFilePath);
		
		try {
			ExcelUtils.setConfigFilePath(configFilePath);
		} catch (Exception e) {
			System.out.println("Exception occured setting config file path. Aborting");
			System.exit(1);
			e.printStackTrace();
		}		
	}
	
	public static void setChromeDriverPathProperty() {
		try {
			chromeDriverPath = ExcelUtils.getCellData(1, 1, settingsSheetName);
			if(chromeDriverPath.equalsIgnoreCase("")) {
				System.out.println("Chrome Driver path is not properly set. Aborting..");
				System.exit(1);
			}
			System.out.println("ChromeDriverPath is : "+ chromeDriverPath);
		} catch (Exception e) {
			System.out.println("Exception occured in reading chrome driver path from config excel. Aborting");
			e.printStackTrace();
		}
		
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);		
	}	
	
	public static void setBaseUrl() {
		URI uri;
		try {
			uri = new URI(driver.getCurrentUrl());
			baseUrl = uri.getScheme() + "://" + uri.getHost();
			System.out.println("base url is "+ baseUrl);
		} catch (URISyntaxException e) {
			System.out.println("Exception occured while trying to retrieve baseUrl. Aborting.");
			e.printStackTrace();
			System.exit(1);			
		}		
	}
	
	public static void setObjectPermissions() {
		
		int numberOfRows = ExcelUtils.getRowCount(objPermissionsSheetName);
		System.out.println("number of Profiles:" +  numberOfRows);
		
		for(int i=1;i <= numberOfRows;i++) {
			String profileId = ExcelUtils.getCellData(i, 0, objPermissionsSheetName);
			System.out.println("Profile Id is: "+ profileId);
			String objId = ExcelUtils.getCellData(i, 1, objPermissionsSheetName);
			System.out.println("Object Id is: "+ objId);
			if(profileId.equalsIgnoreCase("")) {
				System.out.println("Encountered empty row. Aborting");
				System.exit(0);
			}			
			if(objId.equalsIgnoreCase("")) {
				System.out.println("Object Id is blank. skipping the profile "+ profileId);
				continue;
			}			
			if(!profileId.equalsIgnoreCase("") && !objId.equals("")) {
				ObjectPermissionsEditPage.navigateToObjPermissionsEditPage(driver, baseUrl,profileId, wait);
				ObjectPermissionsEditPage.setObjectCreatePermission(driver, objId, Boolean.valueOf(ExcelUtils.getCellData(i, 2, objPermissionsSheetName)), wait);
				ObjectPermissionsEditPage.setObjectReadPermission(driver, objId, Boolean.valueOf(ExcelUtils.getCellData(i, 3, objPermissionsSheetName)), wait);
				ObjectPermissionsEditPage.setObjectEditPermission(driver, objId, Boolean.valueOf(ExcelUtils.getCellData(i, 4, objPermissionsSheetName)), wait);
				ObjectPermissionsEditPage.setObjectDeletePermission(driver, objId, Boolean.valueOf(ExcelUtils.getCellData(i, 5, objPermissionsSheetName)), wait);
				ObjectPermissionsEditPage.setObjectViewAllPermission(driver, objId, Boolean.valueOf(ExcelUtils.getCellData(i, 6, objPermissionsSheetName)), wait);
				ObjectPermissionsEditPage.setObjectModifyAllPermission(driver, objId, Boolean.valueOf(ExcelUtils.getCellData(i, 7, objPermissionsSheetName)), wait);
				ObjectPermissionsEditPage.clickSaveButton(driver, wait);
			}			
			
		}		
	}
	
	public static void setFieldPermissions() {
		
		int numberOfRows = ExcelUtils.getRowCount(fieldPermissionsSheetName);
		System.out.println("number of rows:" +  numberOfRows);
		
		for(int i=1;i <= numberOfRows;i++) {
			String profileId = ExcelUtils.getCellData(i, 0, fieldPermissionsSheetName);
			System.out.println("Profile Id is: "+ profileId);
			String objId = ExcelUtils.getCellData(i, 1, fieldPermissionsSheetName);
			System.out.println("Object Id is: "+ objId);
			if(profileId.equalsIgnoreCase("")) {
				System.out.println("Encountered empty row. Aborting");
				System.exit(0);
			}			
			if(objId.equalsIgnoreCase("")) {
				System.out.println("Object Id is blank. skipping the profile "+ profileId);
				continue;
			}
			String fieldId = ExcelUtils.getCellData(i, 2, fieldPermissionsSheetName);
			System.out.println("field Id is: "+ fieldId);
			if(fieldId.equalsIgnoreCase("")) {
				System.out.println("Field Id is blank. skipping the profile "+ profileId);
				continue;
			}
			if(!profileId.equalsIgnoreCase("") && !objId.equalsIgnoreCase("") && !fieldId.equalsIgnoreCase("")) {
				FLSEditPage.navigateToFLSEditPage(driver, baseUrl,profileId,objId ,wait);
				FLSEditPage.setFieldReadAccess(driver, fieldId, Boolean.valueOf(ExcelUtils.getCellData(i, 3, fieldPermissionsSheetName)), wait);
				FLSEditPage.setFieldEditAccess(driver, fieldId, Boolean.valueOf(ExcelUtils.getCellData(i, 3, fieldPermissionsSheetName)), wait);
				FLSEditPage.clickSaveButton(driver, wait);
			}			
			
		}		
	}	
	

}
