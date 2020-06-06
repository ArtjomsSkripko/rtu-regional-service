package regional.rest

import regional.exception.UnauthorizedException
import regional.mapper.RegionalMapper
import regional.rest.model.RegionalOfferRequestDTO
import regional.service.RegionalService
import spock.lang.Specification

class RegionalControllerSpockTest extends Specification {

    RegionalService service
    RegionalMapper mapper
    RegionalOfferController controller

    def setup() {
        service = Mock(RegionalService.class)
        mapper = Mock(RegionalMapper.class)
        controller = new RegionalOfferController(service, mapper)
    }

    void "call create offer unauthorized"() {
        given:
        RegionalOfferRequestDTO request = new RegionalOfferRequestDTO()
        when:
        controller.createRegionalOffers(request)
        then:
        thrown(UnauthorizedException.class)
    }
}
