package pub.hqs.oauth.dto.client;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthorizationInfo {

    @NotBlank(message = "client_id不能为空")
    @ApiModelProperty(value = "客户端id")
    private String client_id;

    @NotBlank(message = "scope不能为空")
    @ApiModelProperty(value = "应用授权作用域，basic获取userId，userinfo获取用户详细信息")
    private String scope;

    @NotBlank(message = "response_type不能为空")
    @ApiModelProperty(value = "返回类型")
    private String response_type;

    @NotBlank(message = "state不能为空")
    @ApiModelProperty(value = "重定向后会带上state参数")
    private String state;

    @NotBlank(message = "redirect_uri不能为空")
    @ApiModelProperty(value = "回调链接地址")
    private String redirect_uri;
}
