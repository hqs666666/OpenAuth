package pub.hqs.oauth.service.cache;

public interface ICacheService {
    void set(String key, Object value);
    void set(String key, Object value, long time);
    <T extends Object> T get(String key);
    void remove(String key);
}
