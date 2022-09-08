package work.yjoker.homeworkhelper.util;

import java.util.HashMap;

/**
 * @author HeYunjia
 */
public class Holder {

    public static final String NULL_VALUE = "null";
    public static final String ID_HOLDER = "idHolder";
    public static final String PHONE_HOLDER = "phoneHolder";

    public static void set(String key, String value) {
        map.putIfAbsent(key, new ThreadLocal<>());
        map.get(key).set(value);
    }

    public static String get(String key) {
        String value = map.getOrDefault(key, defaultHolder).get();
        return value != null ? value : NULL_VALUE;
    }

    private static final HashMap<String, ThreadLocal<String>> map = new HashMap<>();
    private static final ThreadLocal<String> defaultHolder = new ThreadLocal<>();
}
