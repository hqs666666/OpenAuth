package pub.hqs.oauth.dto.wxopen;

import lombok.Data;
import pub.hqs.oauth.dto.token.ReqAccessToken;
import pub.hqs.oauth.entity.user.WxUser;

import javax.validation.constraints.NotBlank;

@Data
public class ReqCode2Session extends ReqAccessToken {
    @NotBlank(message = "encryptedData不能为空")
    private String encryptedData;
    @NotBlank(message = "iv不能为空")
    private String iv;
    private WxUser userInfo;
}
