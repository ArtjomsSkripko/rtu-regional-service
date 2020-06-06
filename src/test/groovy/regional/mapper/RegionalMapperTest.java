package regional.mapper;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import regional.enumeration.OfferTypeEnum;
import regional.enumeration.PassengerTypeEnum;
import regional.enumeration.TransportTypeEnum;
import regional.model.Address;
import regional.model.RegionalOffer;
import regional.model.RegionalOfferRequest;
import regional.rest.model.AddressDTO;
import regional.rest.model.RegionalOfferDTO;
import regional.rest.model.RegionalOfferRequestDTO;

public class RegionalMapperTest {

    private RegionalMapper mapper = new RegionalMapper();

    @Test
    public void testToOfferDTO() {
        RegionalOffer offer = new RegionalOffer();
        offer.setOriginalPrice(new BigDecimal(10.10));
        offer.setPassenger(PassengerTypeEnum.ADULT);
        offer.setTaxRate(new BigDecimal(23.00));
        Address address = new Address();
        address.setCity("RIGA");
        address.setHomeNumber("99");
        address.setStreetName("Brivibas");
        offer.setAddressFrom(address);
        offer.setAddressTo(address);
        offer.setRouteNumbers(Collections.singletonList(1));
        offer.setTransportTypes(Collections.singletonList(TransportTypeEnum.TRAM));
        offer.setCompanyName("RIGAS_SATIKSME");
        offer.setDiscount(new BigDecimal(1.00));
        offer.setPriceWithDiscount(new BigDecimal(11.00));
        offer.setOfferType(OfferTypeEnum.SINGLE_TICKET);

        RegionalOfferDTO result = mapper.toDTOOffer(offer);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getAddressFrom().getCity(), address.getCity());
        Assert.assertEquals(result.getAddressFrom().getStreetName(), address.getStreetName());
        Assert.assertEquals(result.getAddressFrom().getHomeNumber(), address.getHomeNumber());
        Assert.assertEquals(result.getTaxRate(), offer.getTaxRate().toPlainString());
    }

    @Test
    public void testToDomainRequest() {
        RegionalOfferRequestDTO requestDTO = new RegionalOfferRequestDTO();
        AddressDTO address = new AddressDTO();
        address.setCity("RIGA");
        address.setHomeNumber("99");
        address.setStreetName("Brivibas");
        requestDTO.setAddressFrom(address);
        requestDTO.setAddressTo(address);
        requestDTO.setPassenger(PassengerTypeEnum.ADULT.name());
        requestDTO.setRouteNumbers(Collections.singletonList(1));
        requestDTO.setTransportTypes(Collections.singletonList(TransportTypeEnum.TRAM.name()));
        requestDTO.setCompanyName("RIGAS_SATIKSME");
        requestDTO.setOfferType(OfferTypeEnum.SINGLE_TICKET.name());

        RegionalOfferRequest result = mapper.toDomainRequest(requestDTO);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getAddressFrom().getCity(), address.getCity());
        Assert.assertEquals(result.getAddressFrom().getStreetName(), address.getStreetName());
        Assert.assertEquals(result.getAddressFrom().getHomeNumber(), address.getHomeNumber());
        Assert.assertEquals(result.getOfferType().name(), requestDTO.getOfferType());
    }

}
