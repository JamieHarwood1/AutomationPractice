package configurations;

import driver.BaseDriver;
import driver.DriverProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DriverProperties.class)
public class DriverConfig
{
	@Bean
	@Autowired
	public BaseDriver baseDriver(final DriverProperties driverProperties)
	{
		return new BaseDriver(driverProperties);
	}
}
