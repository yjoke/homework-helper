package work.yjoker.homeworkhelper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;

/**
 * @author HeYunjia
 */
@Configuration
public class OssWrapperConfig {

    @Value("${homework-helper.oss.endpoint}")
    String endpoint;

    @Value("${homework-helper.oss.access-key-id}")
    String accessKeyId;

    @Value("${homework-helper.oss.access-key-secret}")
    String accessKeySecret;

    @Value("${homework-helper.oss.bucket-name}")
    String bucketName;

    @Value("${homework-helper.oss.base-path}")
    String basePath;

    @Bean
    public OssWrapper ossWrapper() {
        return new OssWrapper(endpoint, accessKeyId, accessKeySecret, bucketName, basePath);
    }
}
