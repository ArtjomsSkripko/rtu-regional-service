package regional.mapper;

import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import regional.enumeration.OfferTypeEnum;
import regional.enumeration.PassengerTypeEnum;
import regional.enumeration.TransportTypeEnum;
import regional.model.Address;
import regional.model.RegionalOffer;
import regional.model.RegionalOfferRequest;
import regional.rest.model.AddressDTO;
import regional.rest.model.RegionalOfferDTO;
import regional.rest.model.RegionalOfferRequestDTO;

@Component
public class RegionalMapper {

    public RegionalOfferDTO toDTOOffer(RegionalOffer offer) {
        RegionalOfferDTO dto = new RegionalOfferDTO();

        dto.setAddressFrom(toDTOAddress(offer.getAddressFrom()));
        dto.setCity(offer.getCity());
        dto.setAddressTo(toDTOAddress(offer.getAddressTo()));
        dto.setCompanyName(offer.getCompanyName());
        dto.setId(offer.getId());
        dto.setOfferType(offer.getOfferType().name());
        dto.setPassenger(offer.getPassenger().name());
        dto.setTaxRate(offer.getTaxRate().toString());
        dto.setOriginalPrice((offer.getOriginalPrice().multiply(offer.getDiscount())).toString());
        dto.setTransportTypes(offer.getTransportTypes().stream().map(Enum::toString).collect(Collectors.toList()));
        if(!CollectionUtils.isEmpty(offer.getRouteNumbers()))
        dto.setRouteNumbers(offer.getRouteNumbers().stream().map(Object::toString).collect(Collectors.toList()));

        dto.setPriceWithDiscount(offer.getPriceWithDiscount().toString());
        dto.setDiscount(offer.getDiscount().toString());
        dto.setNumberOfTickets(offer.getNumberOfTickets());
        return dto;
    }

    public RegionalOfferRequest toDomainRequest(RegionalOfferRequestDTO requestDTO) {
        RegionalOfferRequest request = new RegionalOfferRequest();

        request.setAddressFrom(toDomainAddress(requestDTO.getAddressFrom()));
        request.setAddressTo(toDomainAddress(requestDTO.getAddressTo()));
        request.setCity(requestDTO.getCity());
        request.setCompanyName(requestDTO.getCompanyName());
        request.setOfferType(OfferTypeEnum.valueOf(requestDTO.getOfferType()));
        request.setPassenger(PassengerTypeEnum.valueOf(requestDTO.getPassenger()));
        if(!CollectionUtils.isEmpty(requestDTO.getTransportTypes()))
        request.setTransportTypes(requestDTO.getTransportTypes().stream().map(TransportTypeEnum::valueOf).collect(Collectors.toList()));
        request.setRouteNumbers(requestDTO.getRouteNumbers());
        request.setNumberOfTickets(requestDTO.getNumberOfTickets());

        return request;
    }


    private Address toDomainAddress(AddressDTO addressDTO) {
        Address address = new Address();

        address.setCity(addressDTO.getCity());
        address.setHomeNumber(addressDTO.getHomeNumber());
        address.setStreetName(addressDTO.getStreetName());

        return address;
    }

    private AddressDTO toDTOAddress(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setCity(address.getCity());
        addressDTO.setHomeNumber(address.getHomeNumber());
        addressDTO.setStreetName(address.getStreetName());

        return addressDTO;
    }
}
