package configurations;

import automationFramework.BaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverConfig
{
	@Bean
	public BaseDriver baseDriver()
	{
		return new BaseDriver();
	}
}
