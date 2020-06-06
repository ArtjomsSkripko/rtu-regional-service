package configs.steps.jbehave;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import configs.steps.SpringIntegrationTest;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.http.HttpStatus;
import regional.rest.model.AddressDTO;
import regional.rest.model.RegionalOfferRequestDTO;

public class JBehaveSteps extends SpringIntegrationTest {

    private RegionalOfferRequestDTO offerRequest = new RegionalOfferRequestDTO();
    private Response response = null;

    @Given("offer request for trip $city")
    public void createOfferRequestWithDepAndDestCities(String city) {
        AddressDTO addressFrom = new AddressDTO();
        addressFrom.setCity(city);
        AddressDTO addressTo = new AddressDTO();
        addressTo.setCity(city);

        offerRequest.setAddressFrom(addressFrom);
        offerRequest.setAddressTo(addressTo);
        offerRequest.setCity(city);
    }

    @Given("add passenger type $passengerType")
    public void addPassenger(String passengerType) {
        offerRequest.setPassenger(passengerType);
    }

    @Given("add company name $companyName")
    public void addCompanyName(String companyName) {
        offerRequest.setCompanyName(companyName);
    }

    @Given("add offer type $offerType")
    public void addOfferType(String offerType) {
        offerRequest.setOfferType(offerType);
    }

    @Given("set number of tickets to $numberOfTickets")
    public void addNumberOfTickets(Integer numberOfTickets) {
        offerRequest.setNumberOfTickets(numberOfTickets);
    }

    @When("call create offers")
    public void callCreateOffers() {
        response = RestAssured.given()
                .when()
                .contentType("application/json")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRlc3RVc2VyMSIsInVzZXJfaWQiOiIxMjM0MTI1IiwiY29udHJhY3RfbW9kZWwiOiJjb250cmFjdF8xIiwicm9sZSI6IlJFR1VMQVJfVVNFUiIsInRvdWNocG9pbnQiOiJXRUIifQ.vwqvyEYcuZdlmt65QfjwhhZeURNEigCWqfJvGS-kWZA")
                .body(offerRequest)
                .port(8083)
                .post("/v1/regional/");
        response.then().log().all();
    }

    @Given("add transport type $transportType")
    public void addTransportType(String transportType) {
        if (offerRequest.getTransportTypes() == null) {
            List<String> transportTypes = new ArrayList<>();
            transportTypes.add(transportType);
            offerRequest.setTransportTypes(transportTypes);
        } else {
            List<String> transportTypes = offerRequest.getTransportTypes();
            transportTypes.add(transportType);
        }
    }

    @Then("response status is $responseStatus")
    public void responseStatusIs(String responseStatus) {
        response.then().statusCode(HttpStatus.valueOf(responseStatus).value());
    }

    @Then("response has $ticketCount offer")
    public void responseHasTicket(int ticketCount) {
        response.then().body("ticketId", Matchers.hasSize(ticketCount));
    }

    @Then("$ticketNumber offer has route from $depCity to $arrCity$")
    public void ticketHasRoute(int ticketNumber, String depCity, String arrCity) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".depCity", Matchers.equalTo(depCity));
        response.then().body(pathForRequestedTicket + ".destCity", Matchers.equalTo(arrCity));
    }

    @Then("$ticketNumber offer id is not null")
    public void offerIdAssertions(int ticketNumber) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".id", Matchers.notNullValue());
    }

    @Then("$ticketNumber offer has discount value $discount")
    public void discountAssertions(int ticketNumber, String discount) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".discount", Matchers.equalTo(discount));
    }

    @Given("set $addressType street name $streetName and home number $homeNumber")
    public void addAddressDetails(String addressType, String streetName, String homeNumber) {
        if (addressType.equals("departure")) {
            AddressDTO addressFrom = offerRequest.getAddressFrom();
            addressFrom.setStreetName(streetName);
            addressFrom.setHomeNumber(homeNumber);
        } else {
            AddressDTO addressTo = offerRequest.getAddressTo();
            addressTo.setStreetName(streetName);
            addressTo.setHomeNumber(homeNumber);
        }
    }

    @Then("$ticketNumber offer has city $city")
    public void ticketHasRoute(int ticketNumber, String city) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".city", Matchers.equalTo(city));
    }
}
