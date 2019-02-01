package screenshot;

import org.im4java.core.CompareCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ArrayListErrorConsumer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class ScreenshotHelper
{
	private static final String TEMP_FILENAME = "temp.png";
	private static final String COMPARISON_FILENAME = "comparison.png";

	private static final String DESIGN_DIRECTORY = "src/test/resources/designs/";

	private static final double FUZZ = 5.0;

	public static double compareElementToScreenshot(final WebDriver driver,
													final WebElement element,
													final String screenshotFile) throws Throwable
	{
		final Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider())
												 .takeScreenshot(driver, element);

		return compareScreenshots(screenshotFile, screenshot);
	}

	public static double comparePageToScreenshot(final WebDriver driver, final String expected) throws Throwable
	{
		final Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider())
												 .takeScreenshot(driver);

		return compareScreenshots(expected, screenshot);
	}

	private static double compareScreenshots(final String expected, final Screenshot screenshot) throws Exception
	{
		ImageIO.write(screenshot.getImage(), "PNG", new File(TEMP_FILENAME));


		final CompareCmd compare = new CompareCmd();

		final ArrayListErrorConsumer errorConsumer = new ArrayListErrorConsumer();

		compare.setErrorConsumer(errorConsumer);

		final IMOperation op = new IMOperation();

		op.metric("ae");
		op.fuzz(FUZZ, true);
		op.addImage(DESIGN_DIRECTORY + expected);
		op.addImage(TEMP_FILENAME);
		op.addImage(COMPARISON_FILENAME);

		try
		{
			compare.run(op);
			return extractPercentageDifference(errorConsumer, screenshot.getImage());
		}
		catch (final Exception ignored)
		{
			throw new Exception(errorConsumer.getOutput().toString());
		}
	}

	public static double extractPercentageDifference(final ArrayListErrorConsumer errorConsumer,
													 final BufferedImage image) throws Exception
	{
		final List<String> differenceStrings = errorConsumer.getOutput();

		if (differenceStrings.isEmpty())
		{
			throw new Exception("No difference strings generated.");
		}

		final double allPixels = image.getHeight() * image.getWidth();

		return Double.valueOf(differenceStrings.get(0)) * 100.0 / allPixels;
	}
}
