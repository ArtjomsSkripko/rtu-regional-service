package regional.rest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import regional.exception.UnauthorizedException;
import regional.mapper.RegionalMapper;
import regional.rest.model.RegionalOfferRequestDTO;
import regional.service.RegionalService;

import static org.mockito.Mockito.mock;

public class RegionalControllerNGTest {

    private static RegionalService service;
    private static RegionalMapper mapper;
    private static RegionalOfferController controller;

    @BeforeClass
    public static void initiate() {
        mapper = mock(RegionalMapper.class);
        service = mock(RegionalService.class);
        controller = new RegionalOfferController(service, mapper);
    }

    @Test(expectedExceptions = UnauthorizedException.class)
    public void testGetOffersUnauthorized() {
        RegionalOfferRequestDTO request = new RegionalOfferRequestDTO();
        controller.createRegionalOffers(request);
    }
}
