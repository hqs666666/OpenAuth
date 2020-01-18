package pub.hqs.oauth.service.authorization;

import pub.hqs.oauth.dto.AuthorizationInfo;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.entity.auth.Client;
import pub.hqs.oauth.service.IBaseService;

public interface IAuthorizationService extends IBaseService<Client> {
    ResultMsg validClient(AuthorizationInfo dto);

    ResultMsg getRedirectUrl(AuthorizationInfo dto, String userId);

    ResultMsg getClient(AuthorizationInfo dto);

    Boolean hasAuthorizeClient(String userId, String clientId);
}
