package pub.hqs.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserLogin extends AuthorizationInfo {
    @NotNull
    @NotEmpty
    @JsonIgnore
    private String username;

    @NotNull
    @NotEmpty
    @JsonIgnore
    private String password;
}
