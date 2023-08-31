package pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AWAPageFactory {

	WebDriver driver;
	
	@FindBy(xpath="//body/div[@id='page-container']/div[@id='main-body-wrapper']/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[1]/a[1]")
	WebElement guests;
	
	@FindBy(xpath="//body/div[@id='page-container']/div[@id='main-body-wrapper']/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/a[2]")
	WebElement addGuestsButton;
	
	@FindBy(xpath="//body/div[@id='page-container']/div[@id='main-body-wrapper']/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/a[1]")
	WebElement checkAvailabilityButton;
	
	public AWAPageFactory (WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void addGuests() {
		guests.click();
		addGuestsButton.click();
		addGuestsButton.click();
	}
	
	public void checkAvailability() {
		checkAvailabilityButton.click();
	}
}
