package Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Тёма on 01.12.2016.
 */
public class successHandler implements AuthenticationSuccessHandler {

    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());


        if (roles.contains("ROLE_ADMIN")) {

            httpServletResponse.sendRedirect("/goodsAdminPage");
        }
        else if(roles.contains("ROLE_USER")){

            httpServletResponse.sendRedirect("/adminPage");

        }

    }
}


