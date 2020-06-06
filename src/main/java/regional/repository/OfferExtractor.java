package regional.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.RowMapper;
import regional.enumeration.OfferTypeEnum;
import regional.enumeration.TransportTypeEnum;
import regional.model.Address;
import regional.model.RegionalOffer;

import static regional.repository.RegionalRepository.CITY_COLUMN;
import static regional.repository.RegionalRepository.COMPANY_NAME_COLUMN;
import static regional.repository.RegionalRepository.HOME_NUMBER_COLUMN;
import static regional.repository.RegionalRepository.PRICE_WITH_VAT_COLUMN;
import static regional.repository.RegionalRepository.ROUTE_NUMBERS_COLUMN;
import static regional.repository.RegionalRepository.STREET_NAME_COLUMN;
import static regional.repository.RegionalRepository.TAX_RATE_COLUMN;
import static regional.repository.RegionalRepository.TRANSPORT_TYPES_COLUMN;
import static regional.repository.RegionalRepository.TYPE_COLUMN;

public class OfferExtractor implements RowMapper<RegionalOffer> {

    @Override
    public RegionalOffer mapRow(ResultSet rs, int rowNum) throws SQLException {
        RegionalOffer offer = new RegionalOffer();
        offer.setId(UUID.randomUUID().toString());
        offer.setCompanyName(rs.getString(COMPANY_NAME_COLUMN));
        offer.setCity(rs.getString(CITY_COLUMN));
        offer.setOfferType(OfferTypeEnum.valueOf(rs.getString(TYPE_COLUMN)));
        offer.setTaxRate(rs.getBigDecimal(TAX_RATE_COLUMN));
        offer.setOriginalPrice(rs.getBigDecimal(PRICE_WITH_VAT_COLUMN));
        List<String> listOfStringTrainTypes = Arrays.asList(rs.getString(TRANSPORT_TYPES_COLUMN).split(","));
        offer.setTransportTypes(listOfStringTrainTypes.stream().map(TransportTypeEnum::valueOf).collect(Collectors.toList()));
        String routeNumber = rs.getString(ROUTE_NUMBERS_COLUMN);
        if(!rs.wasNull()) {
            List<String> routeNumbersAsString = Arrays.asList(routeNumber.split(","));
            offer.setRouteNumbers(routeNumbersAsString.stream().map(Integer::valueOf).collect(Collectors.toList()));
        }

        return offer;
    }
}
