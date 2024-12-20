package com.automation.framework.tests.flightreservation;

import com.automation.framework.utils.TestGroups;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automation.framework.Assertions;
import com.automation.framework.models.FlightReservationTestData;
import com.automation.framework.pages.flightreservation.FlightsConfirmationPage;
import com.automation.framework.pages.flightreservation.RegistrationPage;
import com.automation.framework.tests.BaseTest;
import com.automation.framework.utils.Config;
import com.automation.framework.utils.Constants;
import com.automation.framework.utils.JsonUtil;

public class FlightReservationTest extends BaseTest {

	private FlightReservationTestData testData;

	@BeforeMethod
	@Parameters({"testDataPath"})
	public void setup(String testDataPath) {
		this.testData = JsonUtil.getTestData(testDataPath, FlightReservationTestData.class);
	}

	@Test(groups = {TestGroups.REGRESSION, TestGroups.FLIGHT_RESERVATION})
	public void reserveFlightTest() {
		RegistrationPage registrationPage = new RegistrationPage(driver);
		registrationPage.goTo(Config.get(Constants.FLIGHT_RESERVATION_URL));
		Assert.assertTrue(registrationPage.isAt());

		FlightsConfirmationPage flightsConfirmationPage = registrationPage.enterFirstName(testData.firstName())
			.enterLastName(testData.lastName())
			.enterEmail(testData.email())
			.enterPassword(testData.password())
			.enterStreet(testData.street())
			.enterCity(testData.city())
			.enterZip(testData.zip())
			.clickRegisterButton()
			.clickGoToFlightSearch()
			.chooseNumberOfPassengers(testData.passengersCount())
			.clickSearchFlights()
			.clickConfirmFlights();

		Assertions assertions = new Assertions();
		assertions.verifyFlightConfirmationNumberExists(flightsConfirmationPage);
		assertions.verifyTotalPriceIsEqualTo(flightsConfirmationPage, testData.expectedPrice());
	}

}
