package regional.service

import org.apache.commons.lang3.tuple.Pair
import regional.authorization.UserToken
import regional.builders.RegionalOfferBuilder
import regional.builders.RegionalOfferRequestBuilder
import regional.builders.UserTokenBuilder
import regional.enumeration.TransportTypeEnum
import regional.exception.OfferNotFoundException
import regional.model.RegionalOffer
import regional.model.RegionalOfferRequest
import regional.repository.RegionalRepository
import spock.lang.Specification

class RegionalServiceSpockTest extends Specification {

    RegionalService service
    RegionalRepository repository

    def setup() {
        repository = Mock(RegionalRepository.class)
        service = new RegionalService(repository)
    }

    void "create offers as regular user"() {
        given:
        RegionalOfferRequest request = new RegionalOfferRequestBuilder().withDefaults().build()
        UserToken token = new UserTokenBuilder().withDefaults().build()

        and:
        repository.getApplicationSpecificDiscount(token.applicationType, token.applicationName) >> new BigDecimal(0.9)

        and:
        repository.getPassengerSpecificDiscount(request.passenger, request.city) >> Pair.of(request.passenger, new BigDecimal(0.5))

        and:
        RegionalOffer offer = new RegionalOfferBuilder().withDefaults().build()
        repository.fetchOffersData(request) >> [offer]

        when:
        List<RegionalOffer> results = service.createOffers(request, token)

        then:
        results != null
        results.get(0).discount == 0.45
        1 * repository.getPassengerSpecificDiscount(request.passenger, request.city) >>
                Pair.of(request.passenger, new BigDecimal(0.5))
        1 * repository.getApplicationSpecificDiscount(token.applicationType, token.applicationName) >>
                new BigDecimal(0.9)
    }

    void "create offers as regular user with offer not found error"() {
        given:
        RegionalOfferRequest request = new RegionalOfferRequestBuilder().withDefaults().build()
        UserToken token = new UserTokenBuilder().withDefaults().build()

        and:
        repository.getApplicationSpecificDiscount(token.applicationType, token.applicationName) >> new BigDecimal(0.9)

        and:
        repository.getPassengerSpecificDiscount(request.passenger, request.city) >> Pair.of(request.passenger, new BigDecimal(0.5))

        and:
        repository.fetchOffersData(request) >> { RegionalOfferRequest offerRequest -> throw new OfferNotFoundException("offer not found")}

        when:
        service.createOffers(request, token)

        then:
        thrown(OfferNotFoundException)
    }


    void "create offers as admin user"() {
        given:
        RegionalOfferRequest request = new RegionalOfferRequestBuilder().withDefaults().build()
        UserToken token = new UserTokenBuilder().withDefaults().role(UserRoleEnum.ADMIN.name()).build()

        and:
        repository.getApplicationSpecificDiscount(token.applicationType, token.applicationName) >> new BigDecimal(0.9)

        and:
        repository.getPassengerSpecificDiscount(request.passenger, request.city) >> Pair.of(request.passenger, new BigDecimal(0.5))

        and:
        RegionalOffer offer = new RegionalOfferBuilder().withDefaults().build()
        repository.fetchOffersData(request) >> [offer]

        when:
        List<RegionalOffer> results = service.createOffers(request, token)

        then:
        results != null
        results.get(0).discount == 0.45
        1 * repository.getPassengerSpecificDiscount(request.passenger, request.city) >>
                Pair.of(request.passenger, new BigDecimal(0.5))
        1 * repository.getApplicationSpecificDiscount(token.applicationType, token.applicationName) >>
                new BigDecimal(0.9)
    }


    void "create offers as conductor user"() {
        given:
        RegionalOfferRequest request = new RegionalOfferRequestBuilder().withDefaults().build()
        UserToken token = new UserTokenBuilder().withDefaults().role(UserRoleEnum.CONDUCTOR.name()).build()

        and:
        repository.getApplicationSpecificDiscount(token.applicationType, token.applicationName) >> new BigDecimal(0.9)

        and:
        repository.getPassengerSpecificDiscount(request.passenger, request.city) >> Pair.of(request.passenger, new BigDecimal(0.5))

        and:
        RegionalOffer offer = new RegionalOfferBuilder().withDefaults().build()
        RegionalOffer offer2 = new RegionalOfferBuilder().withDefaults().build()
        repository.fetchOffersData(request) >> [offer,offer2]

        and:
        RegionalService service = new RegionalService(repository)

        when:
        List<RegionalOffer> results = service.createOffers(request, token)

        then:
        results != null
        results.size() == 2
        notThrown(OfferNotFoundException.class)
        verifyAll {
            results[0].transportTypes.contains(TransportTypeEnum.BUS)
            results[1].transportTypes.contains(TransportTypeEnum.BUS)
            results[0].discount == 0.59
            results[1].discount == 0.59
        }
        1 * repository.getPassengerSpecificDiscount(request.passenger, request.city) >>
                Pair.of(request.passenger, new BigDecimal(0.5))
        1 * repository.getApplicationSpecificDiscount(token.applicationType, token.applicationName) >>
                new BigDecimal(0.9)
    }




}
