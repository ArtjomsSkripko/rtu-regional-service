package regional.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import regional.authorization.UserToken;
import regional.enumeration.PassengerTypeEnum;
import regional.model.RegionalOffer;
import regional.model.RegionalOfferRequest;
import regional.repository.RegionalRepository;

import static regional.service.UserRoleEnum.COMPANY_USER;
import static regional.service.UserRoleEnum.CONDUCTOR;

@Service
public class RegionalService {

    private RegionalRepository repository;

    @Autowired
    public RegionalService(RegionalRepository repository) {
        this.repository = repository;
    }

    public List<RegionalOffer> createOffers(RegionalOfferRequest request, UserToken token) {
        Pair<PassengerTypeEnum, BigDecimal> passengerSpecificDiscount
                = repository.getPassengerSpecificDiscount(request.getPassenger(), request.getCity());

        BigDecimal applicationSpecificDiscount = repository.getApplicationSpecificDiscount(token.getApplicationType(), token.getApplicationName());

        BigDecimal totalDiscount = passengerSpecificDiscount.getValue().multiply(applicationSpecificDiscount);

        BigDecimal companyUserSpecificDiscount = new BigDecimal("1.0");
        if (COMPANY_USER.name().equals(token.getUserRole())) {
            companyUserSpecificDiscount = repository.getCompanyUserSpecificDiscount(token.getCompanyName());
        }

        if (CONDUCTOR.name().equals(token.getUserRole())) {
            totalDiscount = totalDiscount.multiply(new BigDecimal("1.3"));
        }
        List<RegionalOffer> offers = repository.fetchOffersData(request);
        for (RegionalOffer offer : offers) {
            offer.setPassenger(request.getPassenger());
            BigDecimal originalPrice = offer.getOriginalPrice();
            offer.setOriginalPrice(originalPrice.multiply(new BigDecimal(request.getNumberOfTickets())).setScale(2, BigDecimal.ROUND_HALF_EVEN));

            BigDecimal priceWithDiscount = originalPrice.multiply(totalDiscount);
            if (StringUtils.isNotEmpty(token.getCompanyName()) && offer.getCompanyName().equals(token.getCompanyName())) {
                priceWithDiscount = priceWithDiscount.multiply(companyUserSpecificDiscount);
                totalDiscount = totalDiscount.multiply(companyUserSpecificDiscount);
            }
            offer.setDiscount(totalDiscount.setScale(2, BigDecimal.ROUND_HALF_EVEN));
            priceWithDiscount = priceWithDiscount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            offer.setPriceWithDiscount(priceWithDiscount.multiply(new BigDecimal(request.getNumberOfTickets())));
            offer.setNumberOfTickets(request.getNumberOfTickets());
        }

        return offers;
    }
}
