package pub.hqs.oauth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthorizationInfo {

    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "客户端id")
    private String client_id;

    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "应用授权作用域，basic获取userId，userinfo获取用户详细信息")
    private String scope;

    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "返回类型")
    private String response_type;

    @ApiModelProperty(value = "重定向后会带上state参数")
    private String state;

    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "回调链接地址")
    private String redirect_uri;
}
