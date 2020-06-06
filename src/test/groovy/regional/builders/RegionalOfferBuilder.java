package regional.builders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import regional.enumeration.OfferTypeEnum;
import regional.enumeration.PassengerTypeEnum;
import regional.enumeration.TransportTypeEnum;
import regional.model.Address;
import regional.model.RegionalOffer;

public class RegionalOfferBuilder {

    private RegionalOffer template;

    public RegionalOfferBuilder() {
        this.template = new RegionalOffer();
    }

    public RegionalOfferBuilder addressFrom(Address address) {
        template.setAddressFrom(address);
        return this;
    }

    public RegionalOfferBuilder passenger(PassengerTypeEnum passenger) {
        template.setPassenger(passenger);
        return this;
    }

    public RegionalOfferBuilder offerType(OfferTypeEnum offerType) {
        template.setOfferType(offerType);
        return this;
    }

    public RegionalOfferBuilder priceWithDiscount(BigDecimal priceWithDiscount) {
        template.setPriceWithDiscount(priceWithDiscount);
        return this;
    }

    public RegionalOfferBuilder originalPrice(BigDecimal originalPrice) {
        template.setOriginalPrice(originalPrice);
        return this;
    }

    public RegionalOfferBuilder taxRate(BigDecimal taxRate) {
        template.setTaxRate(taxRate);
        return this;
    }

    public RegionalOfferBuilder routeNumbers(List<Integer> routeNumber) {
        template.setRouteNumbers(routeNumber);
        return this;
    }

    public RegionalOfferBuilder transportTypes(List<TransportTypeEnum> transportTypes) {
        template.setTransportTypes(transportTypes);
        return this;
    }

    public RegionalOfferBuilder companyName(String companyName) {
        template.setCompanyName(companyName);
        return this;
    }

    public RegionalOfferBuilder withDefaults() {
        return this
                .addressFrom(new AddressBuilder().withDefaults().build())
                .passenger(PassengerTypeEnum.ADULT)
                .offerType(OfferTypeEnum.SINGLE_TICKET)
                .originalPrice(new BigDecimal(10.10))
                .taxRate(new BigDecimal(23.00))
                .originalPrice(new BigDecimal(10.10))
                .originalPrice(new BigDecimal(10.10))
                .routeNumbers(Arrays.asList(5, 6))
                .transportTypes(Collections.singletonList(TransportTypeEnum.BUS))
                .companyName("RIGAS_SATIKSME")
                ;
    }
    public RegionalOffer build() {
        return template;
    }
}
