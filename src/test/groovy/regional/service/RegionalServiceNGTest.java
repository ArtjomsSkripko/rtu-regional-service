package regional.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import regional.authorization.UserToken;
import regional.builders.RegionalOfferBuilder;
import regional.builders.RegionalOfferRequestBuilder;
import regional.builders.UserTokenBuilder;
import regional.enumeration.PassengerTypeEnum;
import regional.exception.OfferNotFoundException;
import regional.model.RegionalOffer;
import regional.model.RegionalOfferRequest;
import regional.repository.RegionalRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static regional.service.UserRoleEnum.ADMIN;
import static regional.service.UserRoleEnum.CONDUCTOR;
import static regional.service.UserRoleEnum.REGULAR_USER;

public class RegionalServiceNGTest {

    private static RegionalService service;
    private static RegionalRepository repository;
    private static UserToken token;

    @BeforeClass
    public static void initiate() {
        repository = mock(RegionalRepository.class);
        service = new RegionalService(repository);
    }

    @Before
    public void setup(){
        Mockito.reset(repository);
    }

    @BeforeMethod
    public void beforeMethod() {
        token = new UserTokenBuilder().withDefaults().role(REGULAR_USER.name()).build();
    }


    @Test
    public void testGetOffersForRegularUser() {
        RegionalOfferRequest request = new RegionalOfferRequestBuilder().withDefaults().build();
        Mockito.when(repository.getApplicationSpecificDiscount(anyString(), anyString()))
                .thenReturn(new BigDecimal(0.9));
        Mockito.when(repository.getPassengerSpecificDiscount(any(PassengerTypeEnum.class), anyString()))
                .thenReturn(Pair.of(PassengerTypeEnum.SCHOLAR, new BigDecimal(0.5)));
        Mockito.when(repository.fetchOffersData(any()))
                .thenReturn(Collections.singletonList(new RegionalOfferBuilder().withDefaults().build()));

        List<RegionalOffer> results = service.createOffers(request, token);

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(results.get(0).getDiscount(),
                new BigDecimal(0.45).setScale(2, BigDecimal.ROUND_HALF_EVEN));

    }

    @Test(expectedExceptions = OfferNotFoundException.class)
    public void testGetOffersForRegularUserWhenOfferNotFoundException() {
        RegionalOfferRequest request = new RegionalOfferRequestBuilder().withDefaults().build();
        RegionalOffer offer = new RegionalOfferBuilder().withDefaults().build();

        Mockito.when(repository.getApplicationSpecificDiscount(anyString(), anyString()))
                .thenReturn(new BigDecimal(0.9));
        Mockito.when(repository.getPassengerSpecificDiscount(any(PassengerTypeEnum.class), anyString()))
                .thenReturn(Pair.of(PassengerTypeEnum.SCHOLAR, new BigDecimal(0.5)));
        Mockito.when(repository.fetchOffersData(any()))
                .thenThrow(new OfferNotFoundException("error"));

        service.createOffers(request, token);
    }

    @Test
    public void testGetOffersForAdminUser() {
        RegionalOfferRequest request = new RegionalOfferRequestBuilder().withDefaults().build();
        token = new UserTokenBuilder().withDefaults().role(ADMIN.name()).build();
        RegionalOffer offer = new RegionalOfferBuilder().withDefaults().build();

        Mockito.when(repository.getApplicationSpecificDiscount(anyString(), anyString())).thenReturn(new BigDecimal(0.9));
        Mockito.when(repository.getPassengerSpecificDiscount(any(PassengerTypeEnum.class), anyString()))
                .thenReturn(Pair.of(PassengerTypeEnum.SCHOLAR, new BigDecimal(0.5)));
        Mockito.when(repository.fetchOffersData(any())).thenReturn(Collections.singletonList(offer));

        List<RegionalOffer> results = service.createOffers(request, token);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(results.get(0).getDiscount(), new BigDecimal(0.45).setScale(2, BigDecimal.ROUND_HALF_EVEN));
    }

    @Test
    public void testGetOffersForConductorUser() {
        RegionalOfferRequest request = new RegionalOfferRequestBuilder().withDefaults().build();
        token = new UserTokenBuilder().withDefaults().role(CONDUCTOR.name()).build();
        RegionalOffer offer = new RegionalOfferBuilder().withDefaults().build();

        Mockito.when(repository.getApplicationSpecificDiscount(anyString(), anyString())).thenReturn(new BigDecimal(0.9));
        Mockito.when(repository.getPassengerSpecificDiscount(any(PassengerTypeEnum.class), anyString()))
                .thenReturn(Pair.of(PassengerTypeEnum.SCHOLAR, new BigDecimal(0.5)));
        Mockito.when(repository.fetchOffersData(any())).thenReturn(Collections.singletonList(offer));

        List<RegionalOffer> results = service.createOffers(request, token);

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(results.get(0).getDiscount(), new BigDecimal(0.59).setScale(2, BigDecimal.ROUND_HALF_EVEN));
    }
}
