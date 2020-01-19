package pub.hqs.oauth.dto.token;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReqAccessToken{
    @NotBlank(message = "client_id不能为空")
    private String client_id;
    @NotBlank(message = "client_secret不能为空")
    private String client_secret;
    @NotBlank(message = "code不能为空")
    private String code;
    @NotBlank(message = "grant_type不能为空")
    private String grant_type;
}
