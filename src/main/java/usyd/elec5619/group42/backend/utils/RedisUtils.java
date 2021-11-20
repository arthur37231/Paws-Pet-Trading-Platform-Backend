package usyd.elec5619.group42.backend.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static RedisTemplate<String, Object> redis;

    @PostConstruct
    public void redisTemplate() {
        redis = this.redisTemplate;
    }

    public static void createPostEntry(Integer postId) {
        redis.boundValueOps(String.valueOf(postId)).set(1);
    }

    public static void deletePostEntry(Integer postId) {
        redis.delete(String.valueOf(postId));
    }

    public static void incrementPostView(Integer postId) {
        redis.boundValueOps(String.valueOf(postId)).increment();
    }

    public static void insertToken(String token, Integer userId) {
        redis.boundValueOps(token).set(userId, 7, TimeUnit.DAYS);
    }

    public static boolean isTokenValid(String token) {
        Integer id = (Integer) redis.boundValueOps(token).get();
        return id != null;
    }

    public static void expireToken(String token) {
        redis.delete(token);
    }
}
