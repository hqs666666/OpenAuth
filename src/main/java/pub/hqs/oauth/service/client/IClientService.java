package pub.hqs.oauth.service.client;

import pub.hqs.oauth.dto.ResultMsg;
import pub.hqs.oauth.entity.auth.Client;
import pub.hqs.oauth.service.IBaseService;

public interface IClientService extends IBaseService<Client> {
    ResultMsg getClient(String clientId, String secret);
}
