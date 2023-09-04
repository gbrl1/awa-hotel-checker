package website;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

import pagefactory.AWAPageFactory;

import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

/**
 * This small application constantly checks the Anime Weekend Atlanta hotel link
 * for the year 2023 using Selenium.
 * 
 * If a spot opens up, it sends a "text" to my phone number by sending an email
 * to my AT&T email. For example, if you had an AT&T phone with the number
 * 555-555-5555, you would send an email to 5555555555@mms.att.net Then that
 * email would actually send a text to your phone.
 * 
 * @author ghayw
 *
 */
public class AWAWebsiteTest {

	private WebDriver driver;
	private String baseURL;
	AWAPageFactory awaPage;

	private Properties properties;

	private String toEmail;
	private String fromEmail;

	private String username;
	private String password;

	private int emailsSent = 0;

	/**
	 * Sets up ChromeDriver for Selenium and sets credentials form properties file
	 * 
	 * @throws IOException
	 */
	@BeforeClass
	public void setUp() throws IOException {
		driver = new ChromeDriver();
		baseURL = "https://www.marriott.com/event-reservations/reservation-link.mi?id=1684093324553&key=GRP&app=resvlink";

		// This property file is located in /AWAHotelChecker/email.properties
		properties = new Properties();
		try (FileInputStream input = new FileInputStream("email.properties")) {
			properties.load(input);
			fromEmail = properties.getProperty("email.from");
			toEmail = properties.getProperty("email.to");
			username = properties.getProperty("email.username");
			password = properties.getProperty("email.password");
		}

		awaPage = new AWAPageFactory(driver);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseURL);
	}

	/**
	 * Checks four possible dates to stay at for the hotel. Stops after 10 emails
	 * are sent to not overload the phone.
	 * 
	 * @throws Exception
	 */
	@Test
	public void hotelClicks() throws Exception {

		while (true) {
			awaPage.checkThursdayToMonday();
			checkURL(driver.getCurrentUrl(), "Thursday to Modnay");

			awaPage.checkThursdayToSunday();
			checkURL(driver.getCurrentUrl(), "Thursday to Sunday");

			awaPage.checkFridayToMonday();
			checkURL(driver.getCurrentUrl(), "Friday to Monday");

			awaPage.checkFridayToSunday();
			checkURL(driver.getCurrentUrl(), "Friday to Sunday");

			if (emailsSent > 10) {
				break;
			}
		}
	}

	/**
	 * If the link contains "unsuccessful" in the URL, that means that reservations
	 * are full. If this is not the case, it's possible that a spot has opened up
	 * (or a website failure)
	 * 
	 * @param url the URL obtained after pressing the "Check Availability" button
	 * @throws Exception
	 */
	public void checkURL(String url, String days) throws Exception {
		if (!url.contains("unsuccessful")) {
			sendMail(fromEmail, toEmail, "AWA HOTEL CHANGE for " + days, "Link changed to " + url + "\n" + baseURL);
		}

		driver.navigate().back();
	}

	/**
	 * Sends an email if the URL changes to something other than an unsuccessful
	 * results
	 * 
	 * @param from    the one sending the email
	 * @param to      the one receiving the email (i.e. the email that will send a
	 *                text to your phone)
	 * @param subject subject of the email
	 * @param msg     the message of the email
	 * @throws Exception
	 */
	public void sendMail(String from, String to, String subject, String msg) throws Exception {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		message.setContent(multipart);

		Transport.send(message);
		emailsSent++;
	}

	/**
	 * Cleans up variables after executing
	 * 
	 * @throws InterruptedException
	 */
	@AfterClass
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		driver.quit();
	}

}
