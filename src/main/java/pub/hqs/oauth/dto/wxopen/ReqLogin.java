package pub.hqs.oauth.dto.wxopen;

import lombok.Data;
import pub.hqs.oauth.dto.token.ReqAccessToken;

import javax.validation.constraints.NotBlank;

@Data
public class ReqLogin extends ReqAccessToken {
    @NotBlank(message = "username不能为空")
    private String username;
    @NotBlank(message = "password不能为空")
    private String password;
}
