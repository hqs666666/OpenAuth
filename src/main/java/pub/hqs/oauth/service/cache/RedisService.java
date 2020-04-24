package pub.hqs.oauth.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService implements ICacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    public <T extends Object> T get(String key) {
        try{
            if (redisTemplate.hasKey(key)) {
                Object value = redisTemplate.opsForValue().get(key);
                return (T) value;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void remove(String key){
        if (redisTemplate.hasKey(key)){
            redisTemplate.delete(key);
        }
    }
}
