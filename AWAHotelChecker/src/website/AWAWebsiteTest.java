package website;


import org.testng.annotations.Test;

import pagefactory.AWAPageFactory;

import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class AWAWebsiteTest {
	
	private WebDriver driver;
	private String baseURL;
	AWAPageFactory awaPage;


  @BeforeClass
  public void setUp() {
	  driver = new ChromeDriver();
	  baseURL = "https://www.marriott.com/event-reservations/reservation-link.mi?id=1684093324553&key=GRP&app=resvlink";
	  
	  awaPage = new AWAPageFactory(driver);
	  
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  driver.get(baseURL);
  }
  
  @Test
  public void hotelClicks() {
	  awaPage.addGuests();
	  awaPage.checkAvailability();
	  System.out.println(driver.getCurrentUrl());
  }

  @AfterClass
  public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		driver.quit();
  }

}
