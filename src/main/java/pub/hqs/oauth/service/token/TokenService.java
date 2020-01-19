package pub.hqs.oauth.service.token;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.dto.token.ReqAccessToken;
import pub.hqs.oauth.dto.token.RspAccessToken;
import pub.hqs.oauth.entity.auth.*;
import pub.hqs.oauth.entity.user.User;
import pub.hqs.oauth.mapper.*;
import pub.hqs.oauth.service.BaseService;
import pub.hqs.oauth.service.user.IUserService;
import pub.hqs.oauth.utils.AppEnums;
import pub.hqs.oauth.utils.AppStatusCode;
import pub.hqs.oauth.utils.IdHelper;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TokenService extends BaseService<AccessTokenMapper, AccessToken> implements ITokenService {

    @Resource
    private AuthCodeMapper authCodeMapper;
    @Resource
    private ClientMapper clientMapper;
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
        Client client = clientMapper.selectOne(new QueryWrapper<Client>()
                .eq("client_id", dto.getClient_id())
                .eq("client_secret", dto.getClient_secret()));
        if (client == null) return createErrorMsg(AppStatusCode.ClientNotFount);
        if (client.getStatus() != 1) return createErrorMsg(AppStatusCode.ClientDisabled);
        return createResultMsg(client);
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
        Duration duration = Duration.between(authCode.getExpiresIn(), time);
        if (duration.toMillis() <= 0) return createErrorMsg(AppStatusCode.CodeExpired);

        authCode.setStatus(1);
        authCodeMapper.updateById(authCode);
        return createResultMsg(authCode.getUserId());
    }

    @Transactional
    public ResultMsg generateToken(String userId, String scope, ReqAccessToken dto) {
        User user = userService.getById(userId);
        String token = "";
        AccessToken accessToken = new AccessToken();
        accessToken.setId(IdHelper.generateIdentity());
        accessToken.setAccessToken(token);
        accessToken.setUserId(userId);
        accessToken.setUserName(user.getUsername());
        accessToken.setClientId(dto.getClient_id());
        accessToken.setExpiresIn(LocalDateTime.now().plusHours(expiresHour));
        accessToken.setGrantType(dto.getGrant_type());
        accessToken.setScope(scope);
        accessToken.setCreateUser(userId);
        accessToken.setCreateTime(LocalDateTime.now());
        save(accessToken);
        ResultMsg resultMsg = generateRefreshToken(accessToken.getId(), userId);
        RspAccessToken response = new RspAccessToken(token,resultMsg.getData().toString(),expiresHour * 60 * 60);
        return createResultMsg(response);
    }

    public ResultMsg generateRefreshToken(String tokenId, String userId) {
        String token = "";
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
