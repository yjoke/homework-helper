package work.yjoker.homeworkhelper.common.wrapper;

import cn.hutool.core.util.BooleanUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author HeYunjia
 */
public class SmsWrapper {

    /**
     * 需要的配置信息
     *
     * @param secretId    腾讯云账户密钥对
     * @param secretKey   腾讯云账户密钥对
     * @param region      地域信息
     * @param sdkAppId    短信应用 id
     * @param signName    短信签名内容
     * @param templateId  短信模板 id
     * @param countryCode 国家或地区码
     */
    public SmsWrapper(String secretId, String secretKey, String region,
                      String sdkAppId, String signName, String templateId, String countryCode) {
        this.client = new SmsClient(new Credential(secretId, secretKey), region);

        this.sdkAppId = sdkAppId;
        this.signName = signName;
        this.templateId = templateId;
        this.countryCode = countryCode;
    }

    /**
     * 验证验证码是否正确
     *
     * @param phone 手机号
     * @param code 验证码
     * @return 返回是否成功
     */
    public boolean verifyCode(String phone, String code) {
        if (stringRedisTemplate == null) {
            throw new NullPointerException("stringRedisTemplate 为 null, 请先注入");
        }

        if (isEmptyStr(phone) || isEmptyStr(code)) return false;

        String key = CODE_KEY_PREFIX + phone;
        String correct = stringRedisTemplate.opsForValue().get(key);

        return !code.equals(correct);
    }

    /**
     * 向 phone 发送一条短信, 使用 redis 进行后台保存, 2 分钟有效
     *
     * @param phone 发送
     * @return 返回发送是否成功
     */
    public boolean sendMessage(String phone) {
        return !isEmptyStr(sendMessage(phone, true));
    }

    /**
     * 像 phone 发送一条验证码, 返回验证码内容, 2 分钟有效
     *
     * @param phone 发送
     * @param useRedis 是否使用 redis 自动保存, 默认 true
     * @return 返回验证码内容
     */
    public String sendMessage(String phone, boolean useRedis) {
        return sendMessage(phone, DEFAULT_CODE_EXPIRE, DEFAULT_CODE_EXPIRE_UNIT, useRedis);
    }

    /**
     * 发送有效时长 timeout 的验证码到 phone 手机号
     *
     * @param phone   手机号
     * @param timeout 有效期
     * @param unit    单位
     * @param useRedis 是否使用 redis 做内存
     * @return 返回验证码
     */
    public String sendMessage(String phone, long timeout, TimeUnit unit, boolean useRedis) {
        String code = randomCode();
        return sendMessage(phone, code, timeout, unit, useRedis) ? code : null;
    }

    /**
     * 发送有效时长 timeout 的验证码 code 到 phone 手机号
     *
     * @param phone   手机号
     * @param code    验证码
     * @param timeout 有效期
     * @param unit    单位
     * @param useRedis 是否使用 redis 做内存
     * @return 返回发送是否成功
     */
    public boolean sendMessage(String phone, String code, long timeout, TimeUnit unit, boolean useRedis) {

        SendSmsRequest request = createRequest();

        request.setTemplateParamSet(new String[]{code, String.valueOf(unit.toMinutes(timeout))});
        request.setPhoneNumberSet(new String[]{countryCode + phone});

        try {
            boolean sendFlag = SUCCESS_CODE.equals(client.SendSms(request).getSendStatusSet()[0].getCode());
            if (!sendFlag || !useRedis) return sendFlag;

            if (stringRedisTemplate == null) {
                throw new NullPointerException("stringRedisTemplate 为 null, 请先注入");
            }

            return saveInRedis(phone, code, timeout, unit);
        } catch (TencentCloudSDKException e) {
            return false;
        }
    }

    /**
     * 创建一个请求对象
     */
    private SendSmsRequest createRequest() {
        SendSmsRequest request = new SendSmsRequest();

        request.setSmsSdkAppid(sdkAppId);
        request.setSign(signName);
        request.setTemplateID(templateId);

        return request;
    }

    /**
     * 将 code 保存在 redis 中, 有效期为 timeout, 单位 unit
     *
     * @param phone 手机号
     * @param code 验证码
     * @param timeout 有效期
     * @param unit 时间单位
     * @return 返回保存是否成功
     */
    private boolean saveInRedis(String phone, String code, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(CODE_KEY_PREFIX + phone, code, timeout, unit);
        return hasCode(phone);
    }

    /**
     * 判断手机号是否已经发送短信
     *
     * @param phone 手机号
     * @return 返回 redis 中是否存在短信
     */
    public boolean hasCode(String phone) {
        return BooleanUtil.isTrue(stringRedisTemplate.hasKey(CODE_KEY_PREFIX + phone));
    }

    /**
     * 注入 stringRedisTemplate
     */
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 修改短信前缀
     */
    public void setCodeKeyPrefix(String keyPrefix) {
        CODE_KEY_PREFIX = keyPrefix;
    }

    /**
     * 获取一个 6 位验证码
     *
     * @return 返回 length 长度的验证码
     */
    private static String randomCode() {

        StringBuilder sb = new StringBuilder(DEFAULT_CODE_LEN);

        int baseLength = RANDOM_INT.length();

        for(int i = 0; i < DEFAULT_CODE_LEN; ++i) {
            int number = ThreadLocalRandom.current().nextInt(baseLength);
            sb.append(RANDOM_INT.charAt(number));
        }

        return sb.toString();
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 是否是空
     */
    private static boolean isEmptyStr(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 用于操作 redis
     */
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 默认的短信前缀时间
     */
    private static String CODE_KEY_PREFIX = "smsPhoneCodeKey:";

    /**
     * 消息发送
     */
    private final SmsClient client;

    /**
     * 短信应用 id
     */
    private final String sdkAppId;

    /**
     * 短信签名内容
     */
    private final String signName;

    /**
     * 短信模板 id
     */
    private final String templateId;

    /**
     * 国家或地区码
     */
    private final String countryCode;

    /**
     * 短信发送成功的 code 字段标识
     */
    private static final String SUCCESS_CODE = "Ok";

    /**
     * 随机验证码的基数
     */
    private static final String RANDOM_INT = "0123456789";

    /**
     * 默认验证码长度
     */
    private static final int DEFAULT_CODE_LEN = 6;

    /**
     * 默认过期时间
     */
    private static final int DEFAULT_CODE_EXPIRE = 2;

    /**
     * 默认过期时间的单位
     */
    private static final TimeUnit DEFAULT_CODE_EXPIRE_UNIT = TimeUnit.MINUTES;
}
