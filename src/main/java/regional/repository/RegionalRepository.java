package regional.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import regional.enumeration.PassengerTypeEnum;
import regional.exception.OfferNotFoundException;
import regional.model.RegionalOffer;
import regional.model.RegionalOfferRequest;

@Repository
public class RegionalRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(RegionalRepository.class);

    private NamedParameterJdbcOperations namedJdbcTemplate;
    private OfferExtractor offerExtractor = new OfferExtractor();
    public static String COMPANY_NAME_COLUMN = "company_name";
    public static String TYPE_COLUMN = "offer_type";
    public static String TRANSPORT_TYPES_COLUMN = "transport_types";
    public static String ROUTE_NUMBERS_COLUMN = "route_numbers";
    public static String ROUTE_NUMBER_COLUMN = "route_number";
    public static String CITY_COLUMN = "city";
    public static String HOME_NUMBER_COLUMN = "home_number";
    public static String HOME_NUMBER_TO = "home_number_to";
    public static String STREET_NAME_COLUMN = "street_name";
    public static String TAX_RATE_COLUMN = "tax_rate";
    public static String PRICE_WITH_VAT_COLUMN = "price";

    @Autowired
    public RegionalRepository(NamedParameterJdbcOperations namedParameterJdbcTemplate, DataSource dataSource) {
        this.namedJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Pair<PassengerTypeEnum, BigDecimal> getPassengerSpecificDiscount(PassengerTypeEnum passengerType, String city) {
        String sqlQuery = "select discount from regional_discount where passenger_type = '"
                + passengerType.name() + "' and city = '" + city + "'";
        Double foundDiscount = null;
        try {
            foundDiscount = namedJdbcTemplate.queryForObject(sqlQuery, new HashMap<>(), Double.class);
        } catch (Exception e) {
            LOGGER.error("No passenger discount found");
        }

        return Pair.of(passengerType, new BigDecimal(foundDiscount == null ? 1.0 : foundDiscount));
    }

    public BigDecimal getApplicationSpecificDiscount(String applicationType, String applicationName) {
        String sqlQuery = "select discount from application_discount where app_type = '"
                + applicationType + "' and app_name = '" + applicationName + "'";
        Double foundDiscount = null;
        try {
            foundDiscount = namedJdbcTemplate.queryForObject(sqlQuery, new HashMap<>(), Double.class);
        } catch (Exception e) {
            LOGGER.error("No application discount found");
        }

        return foundDiscount != null ? new BigDecimal(foundDiscount) : new BigDecimal(1.0);
    }

    public BigDecimal getCompanyUserSpecificDiscount(String companyName) {
        String sqlQuery = "select discount from company_discount where company = '" + companyName + "'";
        Double foundDiscount = null;
        try {
            foundDiscount = namedJdbcTemplate.queryForObject(sqlQuery, new HashMap<>(), Double.class);
        } catch (Exception e) {
            LOGGER.error("No company discount found");
        }

        return foundDiscount != null ? new BigDecimal(foundDiscount) : new BigDecimal(1.0);
    }

    public List<RegionalOffer> fetchOffersData(RegionalOfferRequest request) {
        StringBuilder queryBuilder = new StringBuilder("select * from regional_offer o");

        List<String> filterCriteria = new ArrayList<>();
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        if (request.getCompanyName() != null) {
            parameters.addValue(COMPANY_NAME_COLUMN, request.getCompanyName());
            filterCriteria.add(createCriteriaString("o", COMPANY_NAME_COLUMN));
        }

        parameters.addValue(TYPE_COLUMN, request.getOfferType().name());
        filterCriteria.add(createCriteriaString("o", TYPE_COLUMN));

        if (!CollectionUtils.isEmpty(request.getTransportTypes())) {
            parameters.addValue(TRANSPORT_TYPES_COLUMN,
                    convertListToStringOfValues(request.getTransportTypes().stream().map(Enum::name).collect(Collectors.toList())));
            filterCriteria.add(createCriteriaString("o", TRANSPORT_TYPES_COLUMN));
        }

        if (!CollectionUtils.isEmpty(request.getRouteNumbers())) {
            parameters.addValue(ROUTE_NUMBERS_COLUMN,
                    convertListToStringOfValues(request.getRouteNumbers().stream().map(Object::toString).collect(Collectors.toList())));
            filterCriteria.add(createCriteriaString("o", ROUTE_NUMBERS_COLUMN));
        }

        parameters.addValue(CITY_COLUMN, request.getCity());
        filterCriteria.add(createCriteriaString("o", CITY_COLUMN));

        if (!CollectionUtils.isEmpty(filterCriteria)) {
            queryBuilder.append(" WHERE ");
            queryBuilder.append(String.join(" AND ", filterCriteria));
        }

        List<RegionalOffer> response = namedJdbcTemplate.query(queryBuilder.toString(), parameters, offerExtractor);

        if (CollectionUtils.isEmpty(response)) {
            throw new OfferNotFoundException("No offers found for current parameter");
        }

        List<RegionalOffer> foundOffers = new ArrayList<>();

        String sql = "select exists(select * from stops where city =:city and route_number in (:route_number)" +
                " and street_name =:street_name and home_number >= :home_number and home_number <= :home_number_to)";
        response.forEach(foundOffer -> {
            boolean departureStopCheckNeeded = request.getAddressFrom().getHomeNumber() != null && request.getAddressFrom().getStreetName() != null;
            boolean destinationStopCheckNeeded = request.getAddressTo().getHomeNumber() != null && request.getAddressTo().getStreetName() != null;
            List<Integer> routeNumbers = foundOffer.getRouteNumbers();

            boolean stopsFound = true;
            if (departureStopCheckNeeded) {
                MapSqlParameterSource param = new MapSqlParameterSource();
                param.addValue(HOME_NUMBER_COLUMN, (Integer.parseInt(request.getAddressFrom().getHomeNumber()) - 10));
                param.addValue(HOME_NUMBER_TO, (Integer.parseInt(request.getAddressFrom().getHomeNumber()) + 10));
                param.addValue(ROUTE_NUMBER_COLUMN, routeNumbers);
                param.addValue(CITY_COLUMN, request.getCity());
                param.addValue(STREET_NAME_COLUMN, request.getAddressFrom().getStreetName());
                Boolean result = namedJdbcTemplate.queryForObject(sql, param, Boolean.class);
                stopsFound = result != null ? result : false;
            }

            if (destinationStopCheckNeeded) {
                MapSqlParameterSource param = new MapSqlParameterSource();
                param.addValue(HOME_NUMBER_COLUMN, (Integer.parseInt(request.getAddressTo().getHomeNumber()) - 10));
                param.addValue(HOME_NUMBER_TO, (Integer.parseInt(request.getAddressTo().getHomeNumber()) + 10));
                param.addValue(ROUTE_NUMBER_COLUMN, routeNumbers);
                param.addValue(CITY_COLUMN, request.getCity());
                param.addValue(STREET_NAME_COLUMN, request.getAddressTo().getStreetName());
                Boolean result = namedJdbcTemplate.queryForObject(sql, param, Boolean.class);
                stopsFound = result != null ? result : false;
            }

            if (stopsFound) {
                foundOffer.setAddressFrom(request.getAddressFrom());
                foundOffer.setAddressTo(request.getAddressTo());
                foundOffers.add(foundOffer);
            }
        });

        if (CollectionUtils.isEmpty(foundOffers)) {
            throw new OfferNotFoundException("No offers found for current parameter");
        }

        return foundOffers;
    }

    private String createCriteriaString(String table, String column) {
        return table + "." + column + " = :" + column;
    }

    private String convertListToStringOfValues(List<String> listOfValues) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < listOfValues.size(); i++) {
            builder.append(listOfValues.get(0));
            if (listOfValues.size() != 1 && i == (listOfValues.size() - 1)) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
