package pub.hqs.oauth.dto.token;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReqRefreshToken {
    @NotBlank(message = "client_id不能为空")
    private String client_id;
    @NotBlank(message = "grant_type不能为空")
    private String grant_type;
    @NotBlank(message = "refresh_token不能为空")
    private String refresh_token;
}
