package by.htp.CityGame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CityBase {
	private static final String URL = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%B3%D0%BE%D1%80%D0%BE%D0%B4%D0%BE%D0%B2_%D0%A0%D0%BE%D1%81%D1%81%D0%B8%D0%B8";

	public List<String> getCities(int step) {
		List<String> citiesNames = new ArrayList<>();
		System.setProperty("webdriver.gecko.driver", "c://driver//geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.get(URL);
		List<WebElement> city = driver.findElements(By.xpath("//table/tbody/tr/td[3]/a"));

		for (int i = 0; i < 1111; i = i + step) {
			String cityElement = city.get(i).getText();
			citiesNames.add(cityElement);
		}
		System.out.println(citiesNames);
		return citiesNames;
	}
}