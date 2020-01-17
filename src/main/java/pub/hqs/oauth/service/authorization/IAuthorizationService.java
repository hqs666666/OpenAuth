package pub.hqs.oauth.service.authorization;

import pub.hqs.oauth.dto.AuthorizationInfo;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.entity.auth.Client;
import pub.hqs.oauth.service.IBaseService;

public interface IAuthorizationService extends IBaseService<Client> {
    ResultMsg getAuthorizationCode(AuthorizationInfo dto);
    ResultMsg getClient(AuthorizationInfo dto);
}
