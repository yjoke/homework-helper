package work.yjoker.homeworkhelper.common;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author HeYunjia
 */

@Component
public class SMSUtil {


    /**
     * 发送 expire 分钟有效的 len 位验证码短信到 phone 手机号
     *
     * @param phone 手机号
     * @param code 验证码位数
     * @param expire 有效时间 min
     * @return 返回发送是否成功
     */
    public static boolean sendMessage(String phone, String code, int expire) {

        SmsClient client = new SmsClient(new Credential(secretId, secretKey), region);

        SendSmsRequest request = new SendSmsRequest();

        request.setSmsSdkAppid(sdkAppId);
        request.setSign(signName);
        request.setTemplateID(templateId);

        request.setTemplateParamSet(new String[]{code, String.valueOf(expire)});
        request.setPhoneNumberSet(new String[]{countryCode + phone});

        try {
            client.SendSms(request);
            return true;
        } catch (TencentCloudSDKException e) {
            return false;
        }
    }


    /**
     * 腾讯云账户密钥对
     */
    private static String secretId;
    private static String secretKey;

    /**
     * 地域信息
     */
    private static String region;

    /**
     * 短信应用 id
     */
    private static String sdkAppId;

    /**
     * 短信签名内容
     */
    private static String signName;

    /**
     * 短信模板 id
     */
    private static String templateId;

    /**
     * 国家或地区码
     */
    private static String countryCode;

    @Value("${homework-helper.sms.secret-id}")
    private void setSecretId(String secretId) {
        SMSUtil.secretId = secretId;
    }

    @Value("${homework-helper.sms.secret-key}")
    private void setSecretKey(String secretKey) {
        SMSUtil.secretKey = secretKey;
    }

    @Value("${homework-helper.sms.region}")
    private void setRegion(String region) {
        SMSUtil.region = region;
    }

    @Value("${homework-helper.sms.sdk-app-id}")
    private void setSdkAppId(String sdkAppId) {
        SMSUtil.sdkAppId = sdkAppId;
    }

    @Value("${homework-helper.sms.sign-name}")
    private void setSingName(String signName) {
        SMSUtil.signName = signName;
    }

    @Value("${homework-helper.sms.template-id}")
    private void setTemplateId(String templateId) {
        SMSUtil.templateId = templateId;
    }

    @Value("${homework-helper.sms.country-code}")
    private void setCountryCode(String countryCode) {
        SMSUtil.countryCode = "+" + countryCode;
    }
}
