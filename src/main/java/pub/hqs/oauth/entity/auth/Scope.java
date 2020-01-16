package pub.hqs.oauth.entity.auth;

import lombok.Data;

@Data
public class Scope {
    private String id;
    private String name;
    private String description;
}
