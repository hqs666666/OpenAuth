package pub.hqs.oauth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class TokenUtils {

    public static String generate(String clientName, String secret, String userId) {
        // 给定一个算法，如HmacSHA-256
        Algorithm alg = Algorithm.HMAC256(secret);

        Date currentTime = new Date();
        String token = JWT.create()
                .withIssuer("OpenAuth") // 发行者
                .withSubject(userId) // 用户身份标识
                .withAudience(clientName) // 用户单位
                .withIssuedAt(currentTime) // 签发时间
                .withNotBefore(currentTime)
                .withExpiresAt(new Date(currentTime.getTime() + 2*3600*1000L)) // 2 hours
                .withJWTId(IdHelper.generateIdentity()) // 分配JWT的ID
                .withClaim("uid",userId)
                .sign(alg);
        return token;
    }

    public static String getClaim(String token,String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
