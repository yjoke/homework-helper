package work.yjoker.homeworkhelper.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取 ip 地址
 */
public class IPUtils {

    /**
     * 获取客户端ip地址 (可以穿透代理)
     *
     * @param request HttpServletRequest
     * @return 返回客户端 ip 地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = null;

        for (String header : HEADERS) {
            ip = request.getHeader(header);
            if (!isEmpty(ip)) break ;
        }

        if (isEmpty(ip)) ip = request.getRemoteAddr();
        if (!isEmpty(ip) && ip.contains(SPLIT)) ip = ip.substring(0, ip.indexOf(SPLIT));
        if (IPV6_LOCALHOST.equals(ip)) ip = IPV4_LOCALHOST;

        return ip;
    }

    /**
     * 判断ip是否为空，空返回true
     *
     * @param ip ip 地址
     * @return 空 true
     */
    private static boolean isEmpty(final String ip) {
        return ip == null || ip.trim().length() == 0 || UNKNOWN.equalsIgnoreCase(ip);
    }

    /**
     * 获取 ip 地址要使用的请求头
     */
    private static final String[] HEADERS = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"
    };

    private static final String SPLIT = ",";
    private static final String UNKNOWN = "unknown";
    private static final String IPV6_LOCALHOST = "0:0:0:0:0:0:0:1";
    private static final String IPV4_LOCALHOST = "127.0.0.1";
}
