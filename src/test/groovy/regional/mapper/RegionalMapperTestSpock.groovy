package regional.mapper

import regional.enumeration.OfferTypeEnum
import regional.enumeration.PassengerTypeEnum
import regional.enumeration.TransportTypeEnum
import regional.model.Address
import regional.model.RegionalOffer
import regional.model.RegionalOfferRequest
import regional.rest.model.AddressDTO
import regional.rest.model.RegionalOfferDTO
import regional.rest.model.RegionalOfferRequestDTO
import spock.lang.Specification

class RegionalMapperTestSpock extends Specification {

    RegionalMapper mapper = new RegionalMapper()

    void "testing toOfferDTO method"() {
        given:
        RegionalOffer offer = new RegionalOffer()
        offer.setOriginalPrice(new BigDecimal(10.10))
        offer.setPassenger(PassengerTypeEnum.ADULT)
        offer.setTaxRate(new BigDecimal(23.00))
        Address address = new Address()
        address.setCity("RIGA")
        address.setHomeNumber("99")
        address.setStreetName("Brivibas")
        offer.setAddressFrom(address)
        offer.setAddressTo(address)
        offer.setRouteNumbers(Collections.singletonList(1))
        offer.setTransportTypes(Collections.singletonList(TransportTypeEnum.TRAM))
        offer.setCompanyName("RIGAS_SATIKSME")
        offer.setDiscount(new BigDecimal(1.00))
        offer.setPriceWithDiscount(new BigDecimal(11.00));
        offer.setOfferType(OfferTypeEnum.SINGLE_TICKET)

        when:
        RegionalOfferDTO result = mapper.toDTOOffer(offer)

        then:
        result != null
        result.getAddressFrom().getCity() == address.getCity()
        result.getAddressFrom().getHomeNumber() == address.getHomeNumber()
        result.getAddressFrom().getStreetName() == address.getStreetName()
        result.getTaxRate() == offer.getTaxRate().toPlainString()
    }

    void "testing ToDomainRequest method"() {
        given:
        RegionalOfferRequestDTO requestDTO = new RegionalOfferRequestDTO()
        AddressDTO address = new AddressDTO()
        address.setCity("RIGA")
        address.setHomeNumber("99")
        address.setStreetName("Brivibas")
        requestDTO.setAddressFrom(address)
        requestDTO.setAddressTo(address)
        requestDTO.setPassenger(PassengerTypeEnum.ADULT.name())
        requestDTO.setRouteNumbers(Collections.singletonList(1))
        requestDTO.setTransportTypes(Collections.singletonList(TransportTypeEnum.TRAM.name()))
        requestDTO.setCompanyName("RIGAS_SATIKSME")
        requestDTO.setOfferType(OfferTypeEnum.SINGLE_TICKET.name())

        when:
        RegionalOfferRequest result = mapper.toDomainRequest(requestDTO)

        then:
        result != null
        result.getAddressFrom().getCity() == address.getCity()
        result.getAddressFrom().getHomeNumber() == address.getHomeNumber()
        result.getAddressFrom().getStreetName() == address.getStreetName()
        result.getOfferType().name() == requestDTO.getOfferType()
    }
}
