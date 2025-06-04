package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger;
	public Properties p;

	@BeforeClass(groups = { "Sanity", "Regression", "Master" })
	@Parameters({ "os", "browser" })
	public void setup(@Optional("windows") String os, String browser) throws IOException {
		// Loading config.properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);

		logger = LogManager.getLogger(this.getClass());

		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// os
			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else {
				System.out.println("No matching os");
				return;
			}

			// browser
			switch (browser.toLowerCase()) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;

			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
				
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;

			default:
				System.out.println("Invalid browser...");
				return;
			}
			driver = new RemoteWebDriver(new URL("http://192.168.2.13:4444/wd/hub"), capabilities);
		}

		if (p.getProperty("execution_env").equalsIgnoreCase("local")) 
		{
			switch (browser.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;

			case "edge":
				driver = new EdgeDriver();
				break;

			default:
				System.out.println("Invalid browser...");
				return;
			}
			
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
	}

	@AfterClass(groups = { "Sanity", "Regression", "Master" })
	void tearDown() {
		driver.quit();
	}

	public String randomeString() {
		return RandomStringUtils.randomAlphabetic(5);
	}

	public String randomeNum() {
		return RandomStringUtils.randomNumeric(5);
	}

	public String randomeAlphaNumberic() {
		return (randomeString() + "@" + randomeNum());
	}

	public String captureScreeen(String tname) {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp;
		File targetFile = new File(targetFilePath);

		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}

}
