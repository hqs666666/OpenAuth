package pub.hqs.oauth.service.authorization;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.hqs.oauth.dto.client.AuthorizationInfo;
import pub.hqs.oauth.dto.client.ClientInfo;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.entity.auth.AuthCode;
import pub.hqs.oauth.entity.auth.Client;
import pub.hqs.oauth.entity.auth.ClientUser;
import pub.hqs.oauth.entity.auth.Scope;
import pub.hqs.oauth.mapper.AuthCodeMapper;
import pub.hqs.oauth.mapper.ClientMapper;
import pub.hqs.oauth.mapper.ClientUserMapper;
import pub.hqs.oauth.mapper.ScopeMapper;
import pub.hqs.oauth.service.BaseService;
import pub.hqs.oauth.utils.AppConstants;
import pub.hqs.oauth.utils.AppStatusCode;
import pub.hqs.oauth.utils.IdHelper;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.time.LocalDateTime;

@Service
public class AuthorizationService extends BaseService<ClientMapper, Client> implements IAuthorizationService {

    @Resource
    private ScopeMapper scopeMapper;
    @Resource
    private AuthCodeMapper authCodeMapper;
    @Resource
    private ClientUserMapper clientUserMapper;

    public ResultMsg validClient(AuthorizationInfo dto) {
        if (dto == null) return createErrorMsg(AppStatusCode.BadRequest);
        if (!dto.getResponse_type().equals(AppConstants.RESPONSE_TYPE_CODE))
            return createErrorMsg(AppStatusCode.UnsupportedResponseType);
        Scope scope = scopeMapper.selectOne(new QueryWrapper<Scope>().eq("name", dto.getScope()));
        if (scope == null) return createErrorMsg(AppStatusCode.UnsupportedScope);

        Client client = getOne(new QueryWrapper<Client>().eq("client_id", dto.getClient_id()));
        if (client == null) return createErrorMsg(AppStatusCode.ClientNotFount);
        if (client.getStatus() != 1) return createErrorMsg(AppStatusCode.ClientDisabled);
        if (!dto.getRedirect_uri().contains(client.getRedirectUri()))
            return createErrorMsg(AppStatusCode.RedirectUriFail);
        return createResultMsg(client);
    }

    public ResultMsg getClient(AuthorizationInfo dto) {
        Client client = getOne(new QueryWrapper<Client>().eq("client_id", dto.getClient_id()));
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setClientId(client.getClientId());
        clientInfo.setClientName(client.getClientName());
        clientInfo.setDescription(client.getDescription());
        clientInfo.setAuthorizationInfo(dto);
        return createResultMsg(clientInfo);
    }

    public Boolean hasAuthorizeClient(String userId, String clientId) {
        ClientUser clientUser = clientUserMapper.selectOne(new QueryWrapper<ClientUser>()
                .eq("user_id", userId).eq("client_id", clientId));
        return clientUser != null;
    }

    @Transactional
    public ResultMsg getRedirectUrl(AuthorizationInfo dto, String userId) {
        String code = IdHelper.generateAuthCode();
        AuthCode entity = new AuthCode();
        entity.setCode(code);
        entity.setClientId(dto.getClient_id());
        entity.setCreateTime(LocalDateTime.now());
        entity.setExpiresIn(LocalDateTime.now().plusMinutes(10));
        entity.setUserId(userId);
        authCodeMapper.insert(entity);

        if (!hasAuthorizeClient(userId, dto.getClient_id())) {
            ClientUser clientUser = new ClientUser();
            clientUser.setClientId(dto.getClient_id());
            clientUser.setScope(dto.getScope());
            clientUser.setUserId(userId);
            clientUserMapper.insert(clientUser);
        }

        String redirectUrl = URLDecoder.decode(dto.getRedirect_uri());
        String joinStr = redirectUrl.contains("?") ? "&" : "?";
        redirectUrl += joinStr + "code=" + code + "&state=" + dto.getState();

        return createResultMsg(redirectUrl);
    }
}
