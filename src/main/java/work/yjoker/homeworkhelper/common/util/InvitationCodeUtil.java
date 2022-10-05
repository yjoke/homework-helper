package work.yjoker.homeworkhelper.common.util;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author HeYunjia
 */
public class InvitationCodeUtil {

    public InvitationCodeUtil(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 获取一个的 6 位邀请码
     * @return 6 位随机的唯一邀请码
     */
    public String getRandomCode() {

        // 获取一个随机池子
        int randomPool = getRandomNum(poolStart, poolEnd);

        // 获取一个随机增长值
        int randomIncr = getRandomNum(randomIncrMin, randomIncrMax);

        // 获取当前池的最大值
        String poolId = prefix + randomPool;
        String nowMaxStr = stringRedisTemplate.opsForValue().get(poolId);
        int nowMax = nowMaxStr != null ? Integer.parseInt(nowMaxStr) : 0;

        // 判断池子是否已经满了
        int newMax = nowMax + randomIncr;
        if (newMax > poolCapacity) return getRandomCode();

        // 更新值
        stringRedisTemplate.opsForValue().set(poolId, String.valueOf(newMax), expire, TimeUnit.DAYS);

        // 返回三十六进制字符串
        return decToSixStr(randomPool * poolCapacity + newMax);
    }

    /**
     * 获取一个随机数, 左闭右开
     * @param start 数的最小值, 包含
     * @param end 数的最大值, 不包含
     * @return 返回一个随机数
     */
    private int getRandomNum(int start, int end) {
        return (int) (Math.random() * (end - start) + start);
    }

    /**
     * 将十进制数转换为 6 为邀请码
     */
    private String decToSixStr(int num) {
        StringBuilder sb = new StringBuilder(InvitationCodeLength);

        decToSixStrCore(num, sb);

        return sb.toString();
    }

    /**
     * 递归将 十进制 转换为 三十六进制
     */
    private void decToSixStrCore(int num, StringBuilder sb) {
        if (num == 0) return;
        if (num < 36) {
            sb.append(meta[num]);
            return;
        }
        decToSixStrCore(num / 36, sb);
        sb.append(meta[num % 36]);
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private StringRedisTemplate stringRedisTemplate;

    private final String prefix;

    /**
     * 三十六进制元字符
     */
    private final char[] meta = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static final int expire = 7;

    private static final int poolStart = 10000;

    private static final int poolEnd = 210000;

    private static final int poolCapacity = 10000;

    private static final int randomIncrMin = 1;

    private static final int randomIncrMax = 10;

    private static final int InvitationCodeLength = 6;
}
