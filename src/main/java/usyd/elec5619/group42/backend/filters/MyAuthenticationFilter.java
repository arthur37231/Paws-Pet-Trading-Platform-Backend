package usyd.elec5619.group42.backend.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import usyd.elec5619.group42.backend.exception.BaseException;
import usyd.elec5619.group42.backend.exception.InvalidParameterException;
import usyd.elec5619.group42.backend.exception.MissingParameterException;
import usyd.elec5619.group42.backend.exception.UnmatchedParameterTypeException;
import usyd.elec5619.group42.backend.utils.AuthUtils;
import usyd.elec5619.group42.backend.utils.Constants;
import usyd.elec5619.group42.backend.utils.ResponseBody;
import usyd.elec5619.group42.backend.utils.ServletUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public MyAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            ServletUtils.jsonToAttribute(request);
            ServletUtils.checkContainsAttributes(request, "username", "password");
        } catch (MissingParameterException e) {
            throw new AuthenticationException(null, e) {};
        }

        try {
            String username = (String) request.getAttribute("username");
            String password = (String) request.getAttribute("password");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username, password
            );
            return authenticationManager.authenticate(authenticationToken);
        } catch (ClassCastException e) {
            throw new AuthenticationException(null, new UnmatchedParameterTypeException("username or password")) {};
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String accessToken = AuthUtils.generateToken(
                Constants.USER_TOKEN_FLAG,
                request.getRequestURL().toString(),
                user.getUsername()
        );

        request.setAttribute("token", accessToken);

        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(
                response.getOutputStream(),
                new ResponseBody(failed.getMessage() == null ? (BaseException) failed.getCause() :
                        new InvalidParameterException("username or password"))
        );
    }
}
