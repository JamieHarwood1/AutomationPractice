package stepDefs;

import driver.BaseDriver;
import configurations.DriverConfig;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

@Component
@ContextConfiguration(
		classes = DriverConfig.class,
		loader = SpringBootContextLoader.class
)
public class TestStepDefs
{
	private final WebDriver driver;

	@Autowired
	public TestStepDefs(final BaseDriver driver)
	{
		this.driver = driver.getDriver();
	}

	@When("^User navigates to (.*)$")
	public void userNavigatesToPage(final String page) throws Throwable
	{
		driver.get(page);
	}

	@Then("^User is on (.*)$")
	public void userIsOnPage(final String page) throws Throwable
	{
		Assert.assertTrue(driver.getCurrentUrl().contains(page));
	}

	@When("^User types (.*) in Google search bar$")
	public void userTypes(final String text) throws Throwable
	{
		final WebElement searchBar = driver.findElement(By.id("lst-ib"));
		searchBar.sendKeys(text);
		searchBar.sendKeys(Keys.RETURN);
	}

	@Then("^Google URL contains (.*)$")
	public void googleSearchBarContains(final String term) throws Throwable
	{
		Assert.assertTrue(driver.getCurrentUrl().contains("q=" + term));
	}
}
