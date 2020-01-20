package pub.hqs.oauth.service.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.entity.auth.Client;
import pub.hqs.oauth.mapper.ClientMapper;
import pub.hqs.oauth.service.BaseService;
import pub.hqs.oauth.utils.AppStatusCode;

@Service
public class ClientService extends BaseService<ClientMapper, Client> implements IClientService {

    public ResultMsg getClient(String clientId, String secret) {
        QueryWrapper query = new QueryWrapper<Client>();
        query.eq("client_id", clientId);
        if (StringUtils.isNotBlank(secret))
            query.eq("client_secret", secret);
        Client client = getOne(query);
        if (client == null) return createErrorMsg(AppStatusCode.ClientNotFount);
        if (client.getStatus() != 1) return createErrorMsg(AppStatusCode.ClientDisabled);
        return createResultMsg(client);
    }
}
