package stepDefs;

import configurations.DriverConfig;
import cucumber.api.java.en.Then;
import driver.BaseDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import screenshot.ScreenshotHelper;

import javax.annotation.PreDestroy;
import java.io.File;

@Component
@ContextConfiguration(
		classes = DriverConfig.class,
		loader = SpringBootContextLoader.class
)
public class ScreenshotStepDefs
{
	private static final int ALLOWED_DIFFERENCE = 5;

	private static final By LOGO_LOCATOR = By.id("hplogo");

	private final WebDriver driver;

	@Autowired
	public ScreenshotStepDefs(final BaseDriver baseDriver)
	{
		driver = baseDriver.getDriver();
	}

	@Then("^Google logo matches design$")
	public void googleLogoMatchesDesign() throws Throwable
	{
		final WebElement element = driver.findElement(LOGO_LOCATOR);

		Assert.assertEquals(0.0,
							ScreenshotHelper.compareElementToScreenshot(driver, element, "google-logo.png"),
							ALLOWED_DIFFERENCE);
	}

	@Then("^Google search page matches design$")
	public void googleSearchPageMatchesDesign() throws Throwable
	{
		Assert.assertEquals(0.0,
							ScreenshotHelper.comparePageToScreenshot(driver, "google-search-page.png"),
							ALLOWED_DIFFERENCE);
	}

	@PreDestroy
	public void deleteScreenshots()
	{
		final File tempFile = new File("temp.png");
		final File comparisonFile = new File("comparison.png");

		tempFile.delete();
		comparisonFile.delete();
	}
}
