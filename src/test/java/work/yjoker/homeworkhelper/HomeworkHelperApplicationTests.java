package work.yjoker.homeworkhelper;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import work.yjoker.homeworkhelper.common.mq.RedisMQ;
import work.yjoker.homeworkhelper.common.mq.RedisMQResult;
import work.yjoker.homeworkhelper.common.wrapper.InvitationCodeWrapper;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;

import javax.annotation.Resource;

@SpringBootTest(classes = HomeworkHelperApplication.class)
class HomeworkHelperApplicationTests {

    @Resource
    private InvitationCodeWrapper invitationCodeWrapper;

    @Resource
    private OssWrapper ossWrapper;

    @Test
    void contextLoads() {
//        System.out.println(ossWrapper.getUrlPrefix());
//        System.out.println(invitationCodeWrapper.getInvitationCode());
    }

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @SneakyThrows
    @Test
    void testRedisMQ() {
//        RedisMQ<String> redisMQ = new RedisMQ<>("testKey", String.class);
//
//        redisMQ.setStringRedisTemplate(stringRedisTemplate);
//
//        redisMQ.create();
//
//        for (int i = 0; i < 10; i++) {
//            redisMQ.offer("test" + i);
//        }
//        System.out.println("数据录入结束");
//        Thread.sleep(10000);
//
//        while (!redisMQ.isEmpty()) {
//            RedisMQResult<String> poll = redisMQ.poll();
//            if (poll == null) break;
//            System.out.print(poll.getValue() + " ");
//            boolean ack = redisMQ.ack(poll);
//
//            System.out.println(ack);
//        }
    }

    @Test
    void testValue() {

//        stringRedisTemplate.opsForValue()
//                .set("name", "addName");
    }
}
