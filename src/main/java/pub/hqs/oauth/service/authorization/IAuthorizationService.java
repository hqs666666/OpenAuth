package pub.hqs.oauth.service.authorization;

import pub.hqs.oauth.dto.AuthorizationCode;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.entity.auth.Client;
import pub.hqs.oauth.service.IBaseService;

public interface IAuthorizationService extends IBaseService<Client> {
    ResultMsg getAuthorizationCode(AuthorizationCode dto);
}
