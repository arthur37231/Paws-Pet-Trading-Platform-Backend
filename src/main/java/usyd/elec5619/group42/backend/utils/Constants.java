package usyd.elec5619.group42.backend.utils;

import com.auth0.jwt.algorithms.Algorithm;

import java.nio.charset.StandardCharsets;

public class Constants {
    public static class Configuration {
        public static final String ORIGIN = "http://localhost:8081";
    }

    public static final String AUTHENTICATED = "authenticated";

    public static final String HASH_SECRET = "elec5619group42";

    public static final String USER_TOKEN_FLAG = "user-token";

    public static final Algorithm ENCODE_ALGORITHM = Algorithm.HMAC256(HASH_SECRET.getBytes(StandardCharsets.UTF_8));

    public static final String REQUEST_TOKEN_HEAD = "Bearer ";

    public static final int HOME_PAGE_CATEGORY_COLUMN_SIZE = 4;

    public static final int HOME_PAGE_LATEST_COLUMN_SIZE = 8;
}
