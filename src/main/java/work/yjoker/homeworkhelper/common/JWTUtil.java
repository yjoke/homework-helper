package work.yjoker.homeworkhelper.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author HeYunjia
 */

@Component
public class JWTUtil {

    private static byte[] key;

    @Value("${homework-helper.token.secret}")
    private void setKey(String secret) {
        key = secret.getBytes();
    }

    private static final String PAYLOAD_KEY_PHONE = "phone";

    /**
     * 获取签名, 有效期一周
     *
     * @param phone 手机号
     * @return token 串
     */
    public static String create(String phone) {
        return JWT.create()
                .setIssuedAt(DateUtil.date())
                .setExpiresAt(DateUtil.nextWeek())
                .setPayload(PAYLOAD_KEY_PHONE, phone)
                .setSigner(JWTSignerUtil.hs256(key))
                .sign();
    }

    /**
     * 验证签名是否正确, 过期
     *
     * @param token token 串
     * @return 返回验证是否通过
     */
    public static boolean verify(String token) {
        if (StrUtil.isBlank(token)) return false;
        try {
            JWTValidator
                    .of(token)
                    .validateAlgorithm(JWTSignerUtil.hs256(key))
                    .validateDate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取荷载段的手机号标识
     *
     * @param token token 串
     * @return 返回手机号
     */
    public static String getPhone(String token) {
        return (String) JWT.of(token).getPayload(PAYLOAD_KEY_PHONE);
    }
}
