package regional.rest;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import regional.authorization.UserToken;
import regional.authorization.Utils;
import regional.exception.UnauthorizedException;
import regional.mapper.RegionalMapper;
import regional.model.RegionalOffer;
import regional.rest.model.RegionalOfferDTO;
import regional.rest.model.RegionalOfferRequestDTO;
import regional.service.RegionalService;

@RestController
@Validated
@Api(protocols = "http, https")
@RequestMapping(value = "v1/regional")
public class RegionalOfferController {

    private RegionalService regionalService;
    private RegionalMapper mapper;

    @Autowired
    public RegionalOfferController(RegionalService regionalService, RegionalMapper mapper) {
        this.regionalService = regionalService;
        this.mapper = mapper;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(
            value = "Generates regional offers",
            notes = "Generates regional offers based on input criteria. If full address and no transport type specified," +
                    " generates offers fo all routes numbers with any transport type that have stations with specified address. " +
                    "If specified route number and transport type selected, then offers with",
            tags = {"Regional offers"}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "InvalidRequestError, code 350: Invalid request"),
            @ApiResponse(code = 500, message = "SomeError")
    })
    public List<RegionalOfferDTO> createRegionalOffers(@RequestBody @Validated RegionalOfferRequestDTO requestDto) {
        UserToken token = Utils.getServiceUser();
        if (token == null) {
           throw new UnauthorizedException("current user is not authorized to fetch offers");
        }
        List<RegionalOffer> offers = regionalService.createOffers(mapper.toDomainRequest(requestDto), Utils.getServiceUser());
        return offers.stream().map(o -> mapper.toDTOOffer(o)).collect(Collectors.toList());
    }
}
