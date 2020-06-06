package regional.builders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import regional.enumeration.OfferTypeEnum;
import regional.enumeration.PassengerTypeEnum;
import regional.enumeration.TransportTypeEnum;
import regional.model.Address;
import regional.model.RegionalOfferRequest;

public class RegionalOfferRequestBuilder {

    private RegionalOfferRequest template;

    public RegionalOfferRequestBuilder() {
        this.template = new RegionalOfferRequest();
    }

    public RegionalOfferRequestBuilder addressFrom(Address address) {
        template.setAddressFrom(address);
        return this;
    }

    public RegionalOfferRequestBuilder passenger(PassengerTypeEnum passenger) {
        template.setPassenger(passenger);
        return this;
    }

    public RegionalOfferRequestBuilder offerType(OfferTypeEnum offerType) {
        template.setOfferType(offerType);
        return this;
    }

    public RegionalOfferRequestBuilder numberOfTickets(Integer numberOfTickets) {
        template.setNumberOfTickets(numberOfTickets);
        return this;
    }

    public RegionalOfferRequestBuilder routeNumbers(List<Integer> routeNumber) {
        template.setRouteNumbers(routeNumber);
        return this;
    }

    public RegionalOfferRequestBuilder transportTypes(List<TransportTypeEnum> transportTypes) {
        template.setTransportTypes(transportTypes);
        return this;
    }

    public RegionalOfferRequestBuilder companyName(String companyName) {
        template.setCompanyName(companyName);
        return this;
    }

    public RegionalOfferRequestBuilder city(String city) {
        template.setCity(city);
        return this;
    }

    public RegionalOfferRequestBuilder withDefaults() {
        return this
                .addressFrom(new AddressBuilder().withDefaults().build())
                .passenger(PassengerTypeEnum.ADULT)
                .offerType(OfferTypeEnum.SINGLE_TICKET)
                .numberOfTickets(2)
                .city("RIGA")
                .routeNumbers(Arrays.asList(5, 6))
                .transportTypes(Collections.singletonList(TransportTypeEnum.BUS))
                .companyName("Company name")
                ;
    }

    public RegionalOfferRequest build() {
        return template;
    }
}
