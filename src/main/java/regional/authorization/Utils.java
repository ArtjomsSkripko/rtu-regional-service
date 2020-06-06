package regional.authorization;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {

    public static ErrorResponse getAuthenticationError() {
        ErrorResponse ret = new ErrorResponse();
        ret.setCode(ServicesConstants.ERROR_ID_AUTHENTICATION_ERROR);
        ret.setName("AuthenticationError");
        ret.setDescription("Authentication failed");
        return ret;
    }

    public static void writeJSONResponse(Object object, HttpServletResponse httpResponse, int httpStatus)
            throws IOException {
        String json = new ObjectMapper().writeValueAsString(object);
        httpResponse.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpResponse.setStatus(httpStatus);
        httpResponse.getWriter().print(json);
    }

    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public static UserToken getServiceUser() {
        return isAuthenticated() && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserToken ?
                (UserToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal() : null;
    }
}
