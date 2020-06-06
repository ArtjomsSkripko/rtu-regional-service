package regional.builders;

import regional.authorization.UserToken;

import static regional.service.UserRoleEnum.REGULAR_USER;

public class UserTokenBuilder {

    private UserToken template;

    public UserTokenBuilder() {
        this.template = new UserToken();
    }

    public UserTokenBuilder appType(String streetName) {
        template.setApplicationType(streetName);
        return this;
    }

    public UserTokenBuilder appName(String appName) {
        template.setApplicationName(appName);
        return this;
    }

    public UserTokenBuilder role(String role) {
        template.setUserRole(role);
        return this;
    }

    public UserTokenBuilder model(String model) {
        template.setContractModel(model);
        return this;
    }

    public UserTokenBuilder contractNumber(String contractNumber) {
        template.setContractNumber(contractNumber);
        return this;
    }

    public UserTokenBuilder tokenId(String tokenId) {
        template.setTokenId(tokenId);
        return this;
    }

    public UserTokenBuilder touchpoint(String touchpoint) {
        template.setTouchpoint(touchpoint);
        return this;
    }

    public UserTokenBuilder userId(String userId) {
        template.setUserId(userId);
        return this;
    }

    public UserTokenBuilder withDefaults() {
        return this
                .appName("Application name")
                .appType("Application type")
                .role(REGULAR_USER.name())
                .appName("Application name")
                ;
    }

    public UserToken build() {
        return template;
    }
}
