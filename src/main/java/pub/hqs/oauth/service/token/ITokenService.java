package pub.hqs.oauth.service.token;

import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.token.ReqAccessToken;
import pub.hqs.oauth.dto.token.ReqRefreshToken;
import pub.hqs.oauth.entity.auth.AccessToken;
import pub.hqs.oauth.entity.auth.Client;
import pub.hqs.oauth.service.IBaseService;

public interface ITokenService extends IBaseService<AccessToken> {
    ResultMsg validClient(ReqAccessToken dto);
    ResultMsg validClientUser(String userId, String clientId);
    ResultMsg validCode(String code, String clientId);
    ResultMsg getAccessToken(String userId, String scope, ReqAccessToken dto);
    ResultMsg refreshToken(ReqRefreshToken req);
    ResultMsg checkToken(String token);
    ResultMsg getTokenBag(String userId, String name, Client client, String grantType, String scope);
}
