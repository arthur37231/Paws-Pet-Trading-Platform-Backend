package usyd.elec5619.group42.backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import usyd.elec5619.group42.backend.exception.UnAuthorisedException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static usyd.elec5619.group42.backend.utils.Constants.*;

public class AuthUtils {
    public static String generateToken(String tokenFlag, String issuer, String username) {
        return JWT.create()
                .withSubject(tokenFlag)
                .withIssuer(issuer)
                .withClaim("username", username)
                .withClaim("authorities", List.of(AUTHENTICATED))
                .sign(ENCODE_ALGORITHM);
    }

    public static String extractToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION).substring(REQUEST_TOKEN_HEAD.length());
    }

    public static DecodedJWT verifyToken(String rawToken) throws UnAuthorisedException {
        if(rawToken == null || !rawToken.startsWith(REQUEST_TOKEN_HEAD))
            throw new UnAuthorisedException();

        String token = rawToken.substring(REQUEST_TOKEN_HEAD.length());
        JWTVerifier verifier = JWT.require(ENCODE_ALGORITHM).build();

        return verifier.verify(token);
    }

    public static String getUsername(DecodedJWT decodedJWT) throws UnAuthorisedException {
        final String username = "username";

        if(claimNotExist(decodedJWT, username)) {
            throw new UnAuthorisedException();
        }

        return decodedJWT.getClaim(username).asString();
    }

    public static List<GrantedAuthority> getAuthorities(DecodedJWT decodedJWT) throws UnAuthorisedException {
        final String authorities = "authorities";

        if(claimNotExist(decodedJWT, authorities)) {
            throw new UnAuthorisedException();
        }

        List<String> authoritiesString = decodedJWT.getClaim(authorities).asList(String.class);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for(String authorityString : authoritiesString) {
            authorityList.add(new SimpleGrantedAuthority(authorityString));
        }
        return authorityList;
    }

    /**
     * If there is no claimName in the token, a non-null claim but null inside is obtained.
     * @see DecodedJWT
     * @see com.auth0.jwt.interfaces.Claim
     */
    private static boolean claimNotExist(DecodedJWT decodedJWT, String claimName) {
        // If there is no claimName in the token, a non-null claim but null inside is obtained.
        return decodedJWT.getClaim(claimName).isNull();
    }
}
