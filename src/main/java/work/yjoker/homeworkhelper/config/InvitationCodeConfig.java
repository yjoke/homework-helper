package work.yjoker.homeworkhelper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import work.yjoker.homeworkhelper.common.wrapper.InvitationCodeWrapper;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */
@Configuration
public class InvitationCodeConfig {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public InvitationCodeWrapper invitationCodeWrapper() {
        InvitationCodeWrapper wrapper = new InvitationCodeWrapper();

        wrapper.setStringRedisTemplate(stringRedisTemplate)
                .setProjectPrefix("needEdit:")
                .setMinNum(2)
                .setAddNum(3)
                .build(true);

        return wrapper;
    }
}
