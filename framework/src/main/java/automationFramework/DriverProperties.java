package automationFramework;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "driver")
public class DriverProperties
{
	private String exeLocation;
	private boolean acceptSSLCertificates;
}
