package pub.hqs.oauth.dto.wxopen;

import lombok.Data;
import pub.hqs.oauth.dto.token.ReqAccessToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReqCode2Session extends ReqAccessToken {
    @NotBlank(message = "nickName不能为空")
    private String nickName;
    @NotBlank(message = "avatarUrl不能为空")
    private String avatarUrl;
    @NotNull(message = "gender不能为空")
    private Integer gender;
    private String country;
    private String province;
    private String city;
    private String unionId;
}
