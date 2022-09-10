package work.yjoker.homeworkhelper.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.yjoker.homeworkhelper.dto.ApiResult;

import java.util.Date;

import static work.yjoker.homeworkhelper.constant.ApiResultConstants.NOT_LOGIN;


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
    private static final char SPACE = ' ';
    private static final String VALIDATION_TYPE = "Bearer";

    /**
     * 获取签名, 有效期一周
     *
     * @param phone 手机号
     * @return token 串
     */
    public static String create(String phone) {
        return create(phone, DateUtil.nextWeek());
    }

    /**
     * 获取签名, expire 时间到期
     *
     * @param phone 手机号
     * @param expire 过期时间
     * @return 返回 token 串
     */
    public static String create(String phone, Date expire) {
        return JWT.create()
                .setIssuedAt(DateUtil.date())
                .setExpiresAt(expire)
                .setPayload(PAYLOAD_KEY_PHONE, phone)
                .setSigner(JWTSignerUtil.hs256(key))
                .sign();
    }

    /**
     * 验证 Authorization 字段是否为 jwt 认证, 并判断 token 串是否合法
     *
     * @param authorization Authorization 字段内容
     * @return 合法则返回 token 串, 非法返回空串
     */
    public static String verifyAuthorization(String authorization) {
        if (StrUtil.isBlank(authorization)) return null;

        int spaceIndex = authorization.indexOf(SPACE);
        String type = authorization.substring(0, spaceIndex);
        if (!type.equals(VALIDATION_TYPE)) return null;

        String token = authorization.substring(spaceIndex + 1);

        return JWTUtil.verify(token) ? token : null;
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
            JWTValidator.of(token)
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
