package work.yjoker.homeworkhelper.common.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.List;


/**
 * 基于 RedisTemplate 实现的消息队列
 *
 * @author HeYunjia
 */
public class RedisMQ<T> {

    /**
     * 构造函数
     *
     * @param key 队列名字
     * @param valueType 存放的数据类型
     */
    public RedisMQ(String key, Class<T> valueType) {
        this.mpKey = key;
        this.valueType = valueType;
    }

    /**
     * 构造队列钱需要先注入 StringRedisTemplate
     *
     * @param template StringRedisTemplate
     */
    public void setStringRedisTemplate(StringRedisTemplate template) {
        this.template = template;
    }

    /**
     * 创建消息队列
     */
    public void create() {
        if (template == null) throw new RuntimeException("need to call setStringRedisTemplate()");

        if (!exist()) createMQ();
    }

    /**
     * 往 id 为 key 的消息队列中, 添加一条消息 obj
     *
     * @param data 要存入消息队列的数据
     */
    public void offer(T data) {
        template.opsForStream().add(mpKey, Collections.singletonMap(MQ_MAP_KEY, toString(data)));
    }

    /**
     * 从消息队列中读取一条数据
     *
     * @return 返回队列中的下一条数据, 如果为空返回 null
     */
    public RedisMQResult<T> poll() {
        return poll(true);
    }

    /**
     * 确认消息队列中的消息 message
     *
     * @param message 从消息队列中获取到的数据
     */
    public boolean ack(RedisMQResult<T> message) {
        if (message == null) throw new RuntimeException("parameter is null");

        Long res = template.execute(SUBMIT_SCRIPT, Collections.singletonList(mpKey), message.getId().getValue());
        return new Long(1).equals(res);
    }

    /**
     * 判断队列是否为空
     */
    public boolean isEmpty() {
        return size() == 0L;
    }

    /**
     * 获取当前队列的长度
     */
    public long size() {
        Long size = template.opsForStream().size(mpKey);
        return size != null ? size : 0;
    }

    /**
     * 判断队列是否存在
     * @return 存在 true, 不存在 false
     */
    public boolean exist() {
        return Boolean.TRUE.equals(template.hasKey(mpKey));
    }

    /**
     * 清空消息队列
     */
    public void clear() {
        destroy();
        createMQ();
    }

    /**
     * 删除队列
     */
    public void destroy() {
        template.delete(Collections.singletonList(mpKey));
    }

    /**
     * 创建消息队列
     */
    private void createMQ() {
        template.opsForStream().createGroup(mpKey, MQ_GROUP_NAME);
    }

    /**
     * 读取队列中的一条数据, 如果不存在, 尝试看 pending-list 中是否存在
     * 如果存在, 将其放在阻塞队列中再次执行 poll, 重复调用最多一次.
     *
     * @param isFirst 是否时第一次
     * @return 返回队列中的下一条数据, 不存在返回 null
     */
    @SuppressWarnings("unchecked")
    private RedisMQResult<T> poll(boolean isFirst) {
        RedisMQResult<T> convert = convert(
                template.opsForStream().read(
                        Consumer.from(MQ_GROUP_NAME, MQ_CONSUMER_NAME),
                        StreamReadOptions.empty().count(1),
                        StreamOffset.create(mpKey, ReadOffset.lastConsumed())));
        if (convert != null) return convert;

        transferPendingList();
        return isFirst ? poll(false) : null;
    }

    /**
     * 从 pending-list 中读取一条数据存到阻塞队列
     */
    private void transferPendingList() {
        try {
            Thread.sleep(BASE_TIME + random());
        } catch (Exception e) {
            throw new RuntimeException("InterruptedException");
        }
        template.execute(TRANSFER_SCRIPT, Collections.singletonList(mpKey));
    }

    /**
     * 将查询 Redis 得到的对象转换为需要的结果对象
     *
     * @param data 查询结果
     * @return MQ 结果对象
     */
    private RedisMQResult<T> convert(List<MapRecord<String, Object, Object>> data) {
        if (data == null || data.isEmpty() || data.get(0) == null) return null;

        return new Result<>(data.get(0).getId(),
                toBean(data.get(0).getValue().get(MQ_MAP_KEY)));
    }

    /**
     * 将 Java 对象序列化为 Json 字符串
     *
     * @param data Java 对象
     * @return 序列化后的字符串
     */
    private String toString(T data) {
        String result;
        try {
            result = MAPPER.writeValueAsString(data);
        } catch (Exception e) {
            throw new ClassCastException();
        }
        return result;
    }

    /**
     * 将 Json 字符串解码为 Java 对象
     *
     * @param value Json 字符串
     * @return Java 对象
     */
    private T toBean(Object value) {
        T result;
        try {
            result = MAPPER.readValue((String) value, valueType);
        } catch (Exception e) {
            throw new ClassCastException();
        }
        return result;
    }

    /**
     * 获取一个 1 秒以内的随机时间
     *
     * @return 随机时间
     */
    private int random() {
        return (int) (Math.random() * RANDOM_TIME);
    }

    /**
     * 返回使用的结果类
     *
     * @param <T> 数据类型
     */
    private static class Result<T> extends RedisMQResult<T> {

        private Result(RecordId id, T value) {
            this.id = id;
            this.value = value;
        }
    }

    /**
     * 队列名字
     */
    private final String mpKey;

    /**
     * 存放的数据类型
     */
    private final Class<T> valueType;

    private StringRedisTemplate template;

    private static final String TRANSFER_SCRIPT_TEXT =
            "local result=redis.call('XREADGROUP'," +
                    "'GROUP','group','consumer','COUNT',1," +
                    "'STREAMS',KEYS[1],'0');" +
                    "if type(result[1][2][1])~='table' then return 0;end;" +
                    "redis.call('XADD',KEYS[1],'*','key',result[1][2][1][2][2]);" +
                    "redis.call('XACK',KEYS[1],'group',result[1][2][1][1]);" +
                    "return 1;";

    private static final DefaultRedisScript<Long> TRANSFER_SCRIPT =
            new DefaultRedisScript<>(TRANSFER_SCRIPT_TEXT, Long.class);

    private static final String SUBMIT_SCRIPT_TEXT =
//            "local id=string.sub(ARGV[1],2,string.len(ARGV[1])-1);" +
                    "if redis.call('XACK',KEYS[1],'group',ARGV[1])~=1 then return 0;end;" +
                    "return redis.call('XDEL',KEYS[1],ARGV[1]);";

    private static final DefaultRedisScript<Long> SUBMIT_SCRIPT =
            new DefaultRedisScript<>(SUBMIT_SCRIPT_TEXT, Long.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final int BASE_TIME = 2000;
    private static final int RANDOM_TIME = 1000;

    private static final String MQ_GROUP_NAME = "group";
    private static final String MQ_CONSUMER_NAME = "consumer";
    private static final String MQ_MAP_KEY = "key";
}
