package automationScripts;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AmazonFeatureTests {

	ChromeDriver driver;

	@BeforeClass
	public void invokeBrowser() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\saman\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://amazon.com");
	}

	@Test(priority = 0)
	public void verifyTitleOfThePage() {
		String expectedTitle = "Amazon.com. Spend less. Smile more.";
		String actualTitle;
		actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@Test(priority = 1)
	public void searchProduct() {
		String productItem = "Apple Watch";
		String category = "Electronics";
		WebElement selDropdown = driver.findElement(By.id("searchDropdownBox"));
		Select selCategory = new Select(selDropdown);
		selCategory.selectByVisibleText(category);
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(productItem);
		driver.findElement(By.xpath("//input[@value='Go']")).click();
	}

	@Test(priority = 2)
	public void getNthProduct() {
		int productItemNumber = 4;
		String xpathExpression = String.format("//div[@data-component-type='s-search-result'][%d]", productItemNumber);
		WebElement nthProduct = driver.findElement(By.xpath(xpathExpression));
		String nthProductResult = nthProduct.getText();
		System.out.println(nthProductResult);
	}

	@Test(priority = 3, enabled = false)
	public void getAllProducts() {
		List<WebElement> allProducts = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
		String productResult;
		for (WebElement product : allProducts) {
			productResult = product.getText();
			System.out.println(productResult);
			System.out.println("----------------------------");
		}
	}

	@Test(priority = 4, enabled = false)
	public void searchAllProductsViaScrollDown() {
		List<WebElement> allProducts = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
		String productResult;
		Actions action = new Actions(driver);
		for (WebElement product : allProducts) {
			action.moveToElement(product).build().perform();
			productResult = product.getText();
			System.out.println(productResult);
			System.out.println("----------------------------");
		}

	}

	@Test(priority = 5)
	public void getAllProductsViaScrollDownUsingJS() {
		List<WebElement> allProducts = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
		String productResult;
		int xCord, yCord;
		Actions action = new Actions(driver);
		for (WebElement product : allProducts) {
			xCord = product.getLocation().x;
			yCord = product.getLocation().y;
			scrollDown(xCord, yCord);
			action.moveToElement(product).build().perform();
			productResult = product.getText();
			System.out.println(productResult);
			System.out.println("----------------------------");
		}
	}

	private void scrollDown(int x, int y) {
		JavascriptExecutor jsEngine;
		jsEngine = (JavascriptExecutor) driver;
		String JsCommand;
		JsCommand = String.format("window.scrollBy(%d,%d)", x, y);
		jsEngine.executeScript(JsCommand);
	}

	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}

}
