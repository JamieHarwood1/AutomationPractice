package driver;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.annotation.PreDestroy;
import java.util.Objects;

@Slf4j
@Data
public class BaseDriver
{
	private static WebDriver driver;
	private DriverProperties properties;

	public BaseDriver(final DriverProperties properties)
	{
		this.properties = properties;

		if (Objects.isNull(driver))
		{
			driver = openBrowser();
		}
	}

	public WebDriver localBrowser()
	{
		final DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, properties.isAcceptSSLCertificates());

		try
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +
														  properties.getExeLocation());
			return new ChromeDriver(capabilities);
		}
		catch (final Exception ex)
		{
			log.error("Could not open browser.", ex);
			throw new IllegalStateException(ex);
		}
	}

	public WebDriver openBrowser()
	{
		final WebDriver driver = localBrowser();
		driver.manage()
			  .window()
			  .maximize();
		return driver;
	}

	public WebDriver getDriver()
	{
		return driver;
	}

	@PreDestroy
	public void close()
	{
		driver.close();
	}
}
