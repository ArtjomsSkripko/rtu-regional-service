package configs.steps;

import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import regional.rest.model.AddressDTO;
import regional.rest.model.RegionalOfferRequestDTO;


public class OfferStepDefs extends SpringIntegrationTest{

    private RegionalOfferRequestDTO offerRequest = new RegionalOfferRequestDTO();
    private Response response = null;

    @Given("^offer request for trip in (.+)$")
    public void createOfferRequestWithDepAndDestCities(String city)  {
        AddressDTO addressFrom = new AddressDTO();
        addressFrom.setCity(city);
        AddressDTO addressTo = new AddressDTO();
        addressTo.setCity(city);

        offerRequest.setAddressFrom(addressFrom);
        offerRequest.setAddressTo(addressTo);
        offerRequest.setCity(city);
    }

    @And("^add offer type (.+)$")
    public void addOfferType(String offerType) {
        offerRequest.setOfferType(offerType);
    }

    @And("^add passenger type (.+)$")
    public void addPassenger(String passengerType) {
        offerRequest.setPassenger(passengerType);
    }

    @And("^add company name (.+)$")
    public void addCompanyName(String companyName) {
        offerRequest.setCompanyName(companyName);
    }

    @And("^set number of tickets to (\\d+)$")
    public void addNumberOfTickets(Integer numberOfTickets) {
        offerRequest.setNumberOfTickets(numberOfTickets);
    }

    @When("^call create offers$")
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

    @And("^add transport type (.+)$")
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

    @Then("^response status is (.+)$")
    public void responseStatusIs(String responseStatus) {
        response.then().statusCode(HttpStatus.valueOf(responseStatus).value());
    }

    @And("^response has (\\d+) offers?$")
    public void responseHasTicket(int ticketCount) {
        response.then().body("ticketId", Matchers.hasSize(ticketCount));
    }

    @And("^(\\d+) offer has city (.+)$")
    public void ticketHasRoute(int ticketNumber, String city) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".city", Matchers.equalTo(city));
    }

    @And("^(\\d+) offer id is not null$")
    public void offerIdAssertions(int ticketNumber) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".id", Matchers.notNullValue());
    }

    @And("^(\\d+) offer has discount value (.+)$")
    public void discountAssertions(int ticketNumber, String discount) {
        String pathForRequestedTicket = "[" + (ticketNumber - 1) + "]";
        response.then().body(pathForRequestedTicket + ".discount", Matchers.equalTo(discount));
    }

    @And("^set (departure|arrival) street name (.+) and home number (.+)$")
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
}
