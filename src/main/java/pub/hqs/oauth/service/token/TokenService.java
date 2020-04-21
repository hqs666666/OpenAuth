package pub.hqs.oauth.service.token;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.token.ReqAccessToken;
import pub.hqs.oauth.dto.token.ReqRefreshToken;
import pub.hqs.oauth.dto.token.RspAccessToken;
import pub.hqs.oauth.entity.auth.*;
import pub.hqs.oauth.entity.user.User;
import pub.hqs.oauth.entity.user.UserInfo;
import pub.hqs.oauth.mapper.*;
import pub.hqs.oauth.service.BaseService;
import pub.hqs.oauth.service.client.IClientService;
import pub.hqs.oauth.service.user.IUserService;
import pub.hqs.oauth.utils.AppEnums;
import pub.hqs.oauth.utils.AppStatusCode;
import pub.hqs.oauth.utils.IdHelper;
import pub.hqs.oauth.utils.TokenUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

@Service
public class TokenService extends BaseService<AccessTokenMapper, AccessToken> implements ITokenService {

    @Resource
    private AuthCodeMapper authCodeMapper;
    @Resource
    private IClientService clientService;
    @Resource
    private ClientUserMapper clientUserMapper;
    @Resource
    private RefreshTokenMapper refreshTokenMapper;
    @Resource
    private IUserService userService;
    @Value("${custom.access-token.expires-hour}")
    private Integer expiresHour;
    @Value("${custom.refresh-token.expires-day}")
    private Integer expiresDay;

    public ResultMsg validClient(ReqAccessToken dto) {
        if (dto == null) return createErrorMsg(AppStatusCode.ParamsValidFail);
        if (!dto.getGrant_type().equals(AppEnums.GrantType.AuthorizationCode.getValue()))
            return createErrorMsg(AppStatusCode.GrantTypeFail);
        ResultMsg resultMsg = clientService.getClient(dto.getClient_id(), dto.getClient_secret());
        return resultMsg;
    }

    public ResultMsg validClientUser(String userId, String clientId) {
        ClientUser clientUser = clientUserMapper.selectOne(new QueryWrapper<ClientUser>()
                .eq("user_id", userId).eq("client_id", clientId));
        if (clientUser == null) return createErrorMsg(AppStatusCode.UserNotAuthorize);
        return createResultMsg(clientUser);
    }

    public ResultMsg validCode(String code, String clientId) {
        AuthCode authCode = authCodeMapper.selectOne(new QueryWrapper<AuthCode>()
                .eq("code", code).eq("client_id", clientId));
        if (authCode == null) return createErrorMsg(AppStatusCode.CodeNotExist);
        if (authCode.getStatus() == 1) return createErrorMsg(AppStatusCode.CodeIsUsed);

        LocalDateTime time = LocalDateTime.now();
        Duration duration = Duration.between(time, authCode.getExpiresIn());
        if (duration.toMillis() <= 0) return createErrorMsg(AppStatusCode.CodeExpired);

        authCode.setStatus(1);
        authCodeMapper.updateById(authCode);
        return createResultMsg(authCode.getUserId());
    }

    @Transactional
    public ResultMsg getAccessToken(String userId, String scope, ReqAccessToken dto) {
        ResultMsg resultMsg = userService.getUserInfo(userId);
        if (!resultMsg.getSuccess()) return resultMsg;
        UserInfo user = (UserInfo) resultMsg.getData();

        resultMsg = clientService.getClient(dto.getClient_id(), "");
        if (!resultMsg.getSuccess()) return resultMsg;
        Client client = (Client) resultMsg.getData();

        resultMsg = getTokenBag(userId, user.getNickname(), client, dto.getGrant_type(), scope);
        return resultMsg;
    }

    public ResultMsg refreshToken(ReqRefreshToken req) {
        if (req == null) return createErrorMsg(AppStatusCode.ParamsValidFail);
        if (!req.getGrant_type().equals(AppEnums.GrantType.RefreshToken.getValue()))
            return createErrorMsg(AppStatusCode.GrantTypeFail);

        RefreshToken refreshToken = refreshTokenMapper.selectOne(new QueryWrapper<RefreshToken>()
                .eq("refresh_token", req.getRefresh_token()));
        if (refreshToken == null) return createErrorMsg(AppStatusCode.RefreshTokenNotFound);

        LocalDateTime time = LocalDateTime.now();
        Duration duration = Duration.between(time, refreshToken.getExpiresIn());
        if (duration.toMillis() <= 0) return createErrorMsg(AppStatusCode.RefreshTokenExpired);

        ResultMsg resultMsg = clientService.getClient(req.getClient_id(), "");
        if (!resultMsg.getSuccess()) return resultMsg;
        Client client = (Client) resultMsg.getData();

        AccessToken accessToken = getById(refreshToken.getTokenId());
        if (accessToken == null) return createErrorMsg(AppStatusCode.RefreshTokenNotFound);

        resultMsg = generateToken(accessToken.getUserId(), accessToken.getUserName(), client, accessToken.getGrantType(), accessToken.getScope());
        if (!resultMsg.getSuccess()) return createErrorMsg(AppStatusCode.TokenFail);

        RspAccessToken response = new RspAccessToken(accessToken.getAccessToken(), req.getRefresh_token(), expiresHour * 60 * 60);
        return createResultMsg(response);
    }

    public ResultMsg checkToken(String token) {
        if (!StringUtils.isNotBlank(token)) return createErrorMsg(AppStatusCode.ParamsValidFail);
        AccessToken accessToken = getOne(new QueryWrapper<AccessToken>().eq("access_token", token));
        if (accessToken == null) return createErrorMsg(AppStatusCode.TokenExpired);

        LocalDateTime time = LocalDateTime.now();
        Duration duration = Duration.between(time, accessToken.getExpiresIn());
        if (duration.toMillis() <= 0) return createErrorMsg(AppStatusCode.TokenExpired);

        ResultMsg resultMsg = clientService.getClient(accessToken.getClientId(), "");
        return resultMsg;
    }

    public ResultMsg getTokenBag(String userId, String name, Client client, String grantType, String scope){
        ResultMsg resultMsg = generateToken(userId, name, client, grantType, scope);
        if (!resultMsg.getSuccess()) return createErrorMsg(AppStatusCode.TokenFail);
        AccessToken accessToken = (AccessToken) resultMsg.getData();

        resultMsg = generateRefreshToken(accessToken.getId(), userId);
        if (!resultMsg.getSuccess()) return createErrorMsg(AppStatusCode.TokenFail);

        RspAccessToken response = new RspAccessToken(accessToken.getAccessToken(), resultMsg.getData().toString(), expiresHour * 60 * 60);
        return createResultMsg(response);
    }

    private ResultMsg generateToken(String userId, String username, Client client, String grantType, String scope) {
        String token = TokenUtils.generate(client.getClientName(), client.getClientSecret(), userId);
        AccessToken accessToken = new AccessToken();
        accessToken.setId(IdHelper.generateIdentity());
        accessToken.setAccessToken(token);
        accessToken.setUserId(userId);
        accessToken.setUserName(username);
        accessToken.setClientId(client.getClientId());
        accessToken.setExpiresIn(LocalDateTime.now().plusHours(expiresHour));
        accessToken.setGrantType(grantType);
        accessToken.setScope(scope);
        accessToken.setCreateUser(userId);
        accessToken.setCreateTime(LocalDateTime.now());
        save(accessToken);
        return createResultMsg(accessToken);
    }

    private ResultMsg generateRefreshToken(String tokenId, String userId) {
        String token = IdHelper.generateIdentity();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(IdHelper.generateIdentity());
        refreshToken.setTokenId(tokenId);
        refreshToken.setRefreshToken(token);
        refreshToken.setExpiresIn(LocalDateTime.now().plusDays(expiresDay));
        refreshToken.setCreateUser(userId);
        refreshToken.setCreateTime(LocalDateTime.now());
        refreshTokenMapper.insert(refreshToken);
        return createResultMsg(token);
    }
}
