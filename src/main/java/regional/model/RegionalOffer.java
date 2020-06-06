package regional.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import regional.enumeration.OfferTypeEnum;
import regional.enumeration.PassengerTypeEnum;
import regional.enumeration.TransportTypeEnum;
import regional.rest.model.AddressDTO;


/**
 * RegionalOfferDTO domain object.
 *
 * @since 1.0.0
 */
public class RegionalOffer {

    private String id = UUID.randomUUID().toString();

    private OfferTypeEnum offerType;
    private String companyName;
    private PassengerTypeEnum passenger;
    private List<TransportTypeEnum> transportTypes;
    private BigDecimal discount;
    private BigDecimal originalPrice;
    private BigDecimal priceWithDiscount;
    private BigDecimal taxRate;
    private List<Integer> routeNumbers;
    private String city;
    private Address addressFrom;
    private Address addressTo;
    private Integer numberOfTickets;

    public RegionalOffer() {
        // default constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OfferTypeEnum getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferTypeEnum offerType) {
        this.offerType = offerType;
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

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public List<Integer> getRouteNumbers() {
        return routeNumbers;
    }

    public void setRouteNumbers(List<Integer> routeNumbers) {
        this.routeNumbers = routeNumbers;
    }


    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    public static String getFullDestinationAddress(Address address) {
        return address.getStreetName() + " " + address.getHomeNumber() + ", " + address.getCity();
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

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    /* Equals & HashCode */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionalOffer that = (RegionalOffer) o;
        return Objects.equals(id, that.id) &&
                offerType == that.offerType &&
                Objects.equals(companyName, that.companyName) &&
                passenger == that.passenger &&
                Objects.equals(transportTypes, that.transportTypes) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(originalPrice, that.originalPrice) &&
                Objects.equals(priceWithDiscount, that.priceWithDiscount) &&
                Objects.equals(taxRate, that.taxRate) &&
                Objects.equals(routeNumbers, that.routeNumbers) &&
                Objects.equals(city, that.city) &&
                Objects.equals(addressFrom, that.addressFrom) &&
                Objects.equals(addressTo, that.addressTo) &&
                Objects.equals(numberOfTickets, that.numberOfTickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, offerType, companyName, passenger, transportTypes, discount, originalPrice, priceWithDiscount, taxRate, routeNumbers, city, addressFrom, addressTo, numberOfTickets);
    }

    @Override
    public String toString() {
        return "RegionalOffer{" +
                "id='" + id + '\'' +
                ", offerType=" + offerType +
                ", companyName='" + companyName + '\'' +
                ", passenger=" + passenger +
                ", transportTypes=" + transportTypes +
                ", discount=" + discount +
                ", originalPrice=" + originalPrice +
                ", priceWithDiscount=" + priceWithDiscount +
                ", taxRate=" + taxRate +
                ", routeNumbers=" + routeNumbers +
                ", city='" + city + '\'' +
                ", addressFrom=" + addressFrom +
                ", addressTo=" + addressTo +
                ", numberOfTickets=" + numberOfTickets +
                '}';
    }
}
