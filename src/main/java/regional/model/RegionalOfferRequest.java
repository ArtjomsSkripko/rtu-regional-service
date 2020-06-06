package regional.model;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import regional.enumeration.OfferTypeEnum;
import regional.enumeration.PassengerTypeEnum;
import regional.enumeration.TransportTypeEnum;
import regional.rest.model.AddressDTO;
import regional.validation.ValidEnum;

public class RegionalOfferRequest {

    private Integer numberOfTickets;
    private String companyName;
    private PassengerTypeEnum passenger;
    private List<TransportTypeEnum> transportTypes;
    private List<Integer> routeNumbers;
    private String city;
    private Address addressFrom;
    private Address addressTo;
    private OfferTypeEnum offerType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionalOfferRequest that = (RegionalOfferRequest) o;
        return Objects.equals(numberOfTickets, that.numberOfTickets) &&
                Objects.equals(companyName, that.companyName) &&
                passenger == that.passenger &&
                Objects.equals(transportTypes, that.transportTypes) &&
                Objects.equals(routeNumbers, that.routeNumbers) &&
                Objects.equals(city, that.city) &&
                Objects.equals(addressFrom, that.addressFrom) &&
                Objects.equals(addressTo, that.addressTo) &&
                offerType == that.offerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfTickets, companyName, passenger, transportTypes, routeNumbers, city, addressFrom, addressTo, offerType);
    }

    public OfferTypeEnum getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferTypeEnum offerType) {
        this.offerType = offerType;
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public PassengerTypeEnum getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerTypeEnum passenger) {
        this.passenger = passenger;
    }

    public List<TransportTypeEnum> getTransportTypes() {
        return transportTypes;
    }

    public void setTransportTypes(List<TransportTypeEnum> transportTypes) {
        this.transportTypes = transportTypes;
    }

    public List<Integer> getRouteNumbers() {
        return routeNumbers;
    }

    public void setRouteNumbers(List<Integer> routeNumbers) {
        this.routeNumbers = routeNumbers;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Address getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(Address addressFrom) {
        this.addressFrom = addressFrom;
    }

    public Address getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(Address addressTo) {
        this.addressTo = addressTo;
    }

    @Override
    public String toString() {
        return "RegionalOfferRequest{" +
                "numberOfTickets=" + numberOfTickets +
                ", companyName='" + companyName + '\'' +
                ", passenger=" + passenger +
                ", transportTypes=" + transportTypes +
                ", routeNumbers=" + routeNumbers +
                ", city='" + city + '\'' +
                ", addressFrom=" + addressFrom +
                ", addressTo=" + addressTo +
                ", offerType=" + offerType +
                '}';
    }
}
