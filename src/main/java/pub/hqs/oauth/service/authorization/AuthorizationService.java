package pub.hqs.oauth.service.authorization;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import pub.hqs.oauth.dto.AuthorizationCode;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.entity.auth.AuthCode;
import pub.hqs.oauth.entity.auth.Client;
import pub.hqs.oauth.entity.auth.Scope;
import pub.hqs.oauth.mapper.AuthCodeMapper;
import pub.hqs.oauth.mapper.ClientMapper;
import pub.hqs.oauth.mapper.ScopeMapper;
import pub.hqs.oauth.service.BaseService;
import pub.hqs.oauth.utils.AppConstants;
import pub.hqs.oauth.utils.AppStatusCode;
import pub.hqs.oauth.utils.IdHelper;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AuthorizationService extends BaseService<ClientMapper, Client> implements IAuthorizationService {

    @Resource
    private ScopeMapper scopeMapper;
    @Resource
    private AuthCodeMapper authCodeMapper;

    public ResultMsg getAuthorizationCode(AuthorizationCode dto) {
        if(!dto.getResponse_type().equals(AppConstants.RESPONSE_TYPE_CODE)) return createErrorMsg(AppStatusCode.UnsupportedResponseType);
        Scope scope = scopeMapper.selectOne(new QueryWrapper<Scope>().eq("name",dto.getScope()));
        if(scope == null) return createErrorMsg(AppStatusCode.UnsupportedScope);

        Client client = getOne(new QueryWrapper<Client>().eq("client_id", dto.getClient_id()));
        if(client == null) return createErrorMsg(AppStatusCode.ClientNotFount);
        if(client.getStatus() != 1) return createErrorMsg(AppStatusCode.ClientDisabled);

        String code = IdHelper.generateAuthCode();
        AuthCode entity = new AuthCode();
        entity.setCode(code);
        entity.setClientId(dto.getClient_id());
        entity.setCreateTime(new Date());
        authCodeMapper.insert(entity);
        return createResultMsg(code);
    }
}
