package work.yjoker.homeworkhelper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import work.yjoker.homeworkhelper.common.wrapper.SmsWrapper;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */
@Configuration
public class SmsWrapperConfig {

    @Bean
    public SmsWrapper smsWrapper() {
        SmsWrapper smsWrapper =
                new SmsWrapper(secretId, secretKey, region, sdkAppId, signName, templateId, countryCode);

        smsWrapper.setCodeKeyPrefix(codeKeyPrefix);
        smsWrapper.setStringRedisTemplate(stringRedisTemplate);

        return smsWrapper;
    }

    /**
     * 腾讯云账户密钥对
     */
    @Value("${homework-helper.sms.secret-id}")
    private String secretId;

    @Value("${homework-helper.sms.secret-key}")
    private String secretKey;

    /**
     * 地域信息
     */
    @Value("${homework-helper.sms.region}")
    private String region;

    /**
     * 短信应用 id
     */
    @Value("${homework-helper.sms.sdk-app-id}")
    private String sdkAppId;

    /**
     * 短信签名内容
     */
    @Value("${homework-helper.sms.sign-name}")
    private String signName;

    /**
     * 短信模板 id
     */
    @Value("${homework-helper.sms.template-id}")
    private String templateId;

    /**
     * 国家或地区码
     */
    @Value("${homework-helper.sms.country-code}")
    private String countryCode;

    /**
     * 存入 redis 的键值前缀
     */
    @Value("${homework-helper.sms.code-key-prefix}")
    private String codeKeyPrefix;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
}
