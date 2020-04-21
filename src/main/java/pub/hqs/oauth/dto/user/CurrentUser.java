package pub.hqs.oauth.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUser {
    private String id;
    private String username;
    private String mobile;
}
