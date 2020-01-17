package pub.hqs.oauth.service.cache;

import org.springframework.stereotype.Service;

@Service
public class RedisService implements ICacheService {

    public void set(String key, Object value){

    }

    public <T extends Object> T get(String key){
        Object value = new Object();
        return (T)value;
    }
}
