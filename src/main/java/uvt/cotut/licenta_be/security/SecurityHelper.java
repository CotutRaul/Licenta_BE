package uvt.cotut.licenta_be.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uvt.cotut.licenta_be.model.User;

public class SecurityHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityHelper.class);

    private SecurityHelper(){
        //do nothing
    }

    public static User getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            LOGGER.warn("Authentication is null");
            return null;
        } else {
            Object principal = authentication.getPrincipal();
            if (principal == null) {
                LOGGER.warn("Principal is null");
                return null;
            } else {
                return principal instanceof User ? (User)principal : null;
            }
        }
    }
}
