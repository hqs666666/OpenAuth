package pub.hqs.oauth.service.cache;

public interface ICacheService {
    void set(String key, Object value);
    <T extends Object> T get(String key);
}
