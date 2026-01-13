package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_01_Setup_environment {

	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		// Thiết lập geckodriver path
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		// Mở trang Google
		driver.get("https://www.google.com/");
	}

	@Test
	public void TC_01_Title() {
		// Kiểm tra tiêu đề trang
		String actualTitle = driver.getTitle();
		Assert.assertTrue(actualTitle.contains("Google"), "Title không chứa 'Google'");
	}

	@Test
	public void TC_02_SearchBoxVisible() {
		// Kiểm tra search box hiển thị
		WebElement searchBox = driver.findElement(By.name("q"));
		Assert.assertTrue(searchBox.isDisplayed(), "Search box không hiển thị");
	}

	@Test
	public void TC_03_SearchKeyword() {
		// Thực hiện search từ khóa "Selenium WebDriver"
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("Selenium WebDriver");
		searchBox.sendKeys(Keys.ENTER);

		// Kiểm tra kết quả có chứa từ khóa
		WebElement firstResult = driver.findElement(By.cssSelector("h3"));
		String resultText = firstResult.getText();
		Assert.assertTrue(resultText.toLowerCase().contains("selenium"), "Kết quả search không chứa từ 'selenium'");
	}

	@AfterClass
	public void afterClass() {
		if (driver != null) {
			driver.quit();
		}
	}
}
