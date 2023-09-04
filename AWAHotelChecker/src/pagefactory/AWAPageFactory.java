package pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Contains all of the xpaths for the hotel website and clicks through the page.
 * 
 * @author ghayw
 *
 */
public class AWAPageFactory {

	WebDriver driver;

	@FindBy(xpath = "//body/div[@id='page-container']/div[@id='main-body-wrapper']/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[1]/a[1]")
	WebElement guests;

	@FindBy(xpath = "//body/div[@id='page-container']/div[@id='main-body-wrapper']/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/a[2]")
	WebElement addGuestsButton;

	@FindBy(xpath = "//body/div[@id='page-container']/div[@id='main-body-wrapper']/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/span[6]")
	WebElement dates;

	@FindBy(xpath = "//div[contains(text(),'26')]")
	WebElement thursday;

	@FindBy(xpath = "//div[contains(text(),'27')]")
	WebElement friday;

	@FindBy(xpath = "//div[contains(text(),'29')]")
	WebElement sunday;

	@FindBy(xpath = "//body/div[@id='page-container']/div[@id='main-body-wrapper']/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/a[1]")
	WebElement checkAvailabilityButton;

	public AWAPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Checks Thursday through Monday. This is the default value so no need to click
	 * any days
	 */
	public void checkThursdayToMonday() {
		addGuests();
		checkAvailability();
	}

	/**
	 * Checks Thursday through Sunday. Since Thursday is the default value, it needs
	 * to be clicked twice to confirm that it's the starting day
	 * 
	 * @throws InterruptedException
	 */
	public void checkThursdayToSunday() throws InterruptedException {
		clickDates();
		thursday.click();
		thursday.click();
		sunday.click();
		checkAvailability();
	}

	/**
	 * Checks Friday to Monday. Since Monday is the default ending date, there is no
	 * need to click it.
	 * 
	 * @throws InterruptedException
	 */
	public void checkFridayToMonday() throws InterruptedException {
		clickDates();
		friday.click();
		checkAvailability();
	}

	/**
	 * Checks Friday to Sunday.
	 * 
	 * @throws InterruptedException
	 */
	public void checkFridayToSunday() throws InterruptedException {
		clickDates();
		friday.click();
		sunday.click();
		checkAvailability();
	}

	/**
	 * Adds two more guests.
	 */
	public void addGuests() {
		guests.click();
		addGuestsButton.click();
		addGuestsButton.click();
	}

	/**
	 * Clicks dates but adds a delay so that it can detect the days on the calendar
	 * 
	 * @throws InterruptedException
	 */
	public void clickDates() throws InterruptedException {
		dates.click();
		Thread.sleep(1000);
	}

	/**
	 * Clicks the Check Availability button
	 */
	public void checkAvailability() {
		checkAvailabilityButton.click();
	}
}
