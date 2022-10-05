package work.yjoker.homeworkhelper.common.wrapper;

import org.springframework.data.redis.core.StringRedisTemplate;
import work.yjoker.homeworkhelper.common.util.InvitationCodeUtil;
import work.yjoker.homeworkhelper.common.mq.RedisMQ;
import work.yjoker.homeworkhelper.common.mq.RedisMQResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author HeYunjia
 */
public class InvitationCodeWrapper {

    /**
     * 获取一个随机邀请码
     */
    public String getInvitationCode() {
        RedisMQResult<String> poll = redisMQ.poll();
        if (poll == null) {
            generate();
            return codeUtil.getRandomCode();
        }
        redisMQ.ack(poll);

        if (redisMQ.size() < minNum) generate();

        return poll.getValue();
    }

    /**
     * 输入 stringRedisTemplate
     */
    public InvitationCodeWrapper setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.template = stringRedisTemplate;
        return this;
    }

    /**
     * 设置项目前缀标识
     */
    public InvitationCodeWrapper setProjectPrefix(String projectPrefix) {
        this.projectPrefix = projectPrefix;
        return this;
    }

    /**
     * 设置最小可用邀请码
     */
    public InvitationCodeWrapper setMinNum(int minNum) {
        this.minNum = minNum;
        return this;
    }

    /**
     * 设置每次新增的数量
     */
    public InvitationCodeWrapper setAddNum(int addNum) {
        this.addNum = addNum;
        return this;
    }

    /**
     * 初始化
     * @param preheat 是否进行预热
     */
    public void build(boolean preheat) {
        codeUtil = new InvitationCodeUtil(this.projectPrefix + POOL_PREFIX);
        codeUtil.setStringRedisTemplate(this.template);

        redisMQ = new RedisMQ<>(this.projectPrefix + MQ_KEY, String.class);
        redisMQ.setStringRedisTemplate(this.template);
        redisMQ.create();

        if (preheat) generate();
    }


    /**
     * 异步线程生成一组邀请码
     */
    private void generate() {
        CODE_GENERATOR.submit(() -> {
            for (int i = 0; i < addNum; i++) {
                String randomCode = codeUtil.getRandomCode();
                redisMQ.offer(randomCode);
            }
        });
    }

    private InvitationCodeUtil codeUtil;

    private StringRedisTemplate template;

    private String projectPrefix = "";

    private static final String POOL_PREFIX = "pool:";

    private static final String MQ_KEY = "codeMq";

    private RedisMQ<String> redisMQ;

    private int addNum = 10;

    private int minNum = 5;

    private static final ExecutorService CODE_GENERATOR = Executors.newSingleThreadExecutor();

}
