package usyd.elec5619.group42.backend.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import usyd.elec5619.group42.backend.exception.UnAuthorisedException;
import usyd.elec5619.group42.backend.utils.AuthUtils;
import usyd.elec5619.group42.backend.utils.RedisUtils;
import usyd.elec5619.group42.backend.utils.ResponseBody;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static usyd.elec5619.group42.backend.utils.Constants.REQUEST_TOKEN_HEAD;

public class MyAuthorisationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        if(shouldFiltered(httpServletRequest.getServletPath()))
        {
            String authorisationHeader = httpServletRequest.getHeader(AUTHORIZATION);
            if(authorisationHeader != null && authorisationHeader.startsWith(REQUEST_TOKEN_HEAD)) {
                try {
                    DecodedJWT decodedJWT = AuthUtils.verifyToken(authorisationHeader);

                    String token = authorisationHeader.substring(REQUEST_TOKEN_HEAD.length());
                    if(!RedisUtils.isTokenValid(token)) {
                        throw new UnAuthorisedException();
                    }

                    String username = AuthUtils.getUsername(decodedJWT);
                    httpServletRequest.setAttribute("username", username);
                    List<GrantedAuthority> authorities = AuthUtils.getAuthorities(decodedJWT);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (Exception e) {
                    produceUnauthorisedResponse(httpServletResponse);
                    return;
                }
            } else {
                produceUnauthorisedResponse(httpServletResponse);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void produceUnauthorisedResponse(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(
                httpServletResponse.getOutputStream(),
                new ResponseBody(new UnAuthorisedException())
        );
    }

    private static final Set<String> noFilterPath = new HashSet<>();

    static {
        noFilterPath.addAll(Arrays.asList(
                "/auth/login",
                "/auth/register",
                "/swagger",
                "/v3",
                "/home",
                "/websocket",
                "/favicon.ico",
                "/petPost/index",
                "/weather/Sydney",
                "/weather/Melbourne",
                "/weather/Canberra"
        ));
    }

    private boolean shouldFiltered(String path) {
        for(String rule : noFilterPath) {
            if(path.startsWith(rule)) {
                return false;
            }
        }
        return true;
    }
}
