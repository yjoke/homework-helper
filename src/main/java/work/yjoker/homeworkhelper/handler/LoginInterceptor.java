package work.yjoker.homeworkhelper.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import work.yjoker.homeworkhelper.common.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author HeYunjia
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final char SPACE = ' ';
    private static final String VALIDATION_TYPE = "Bearer";

    /**
     * 验证登录以外的请求, 是否登录
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) return true;

        String authorization = request.getHeader(Header.AUTHORIZATION.toString());
        if (StrUtil.isBlank(authorization)) return false;

        int spaceIndex = authorization.indexOf(SPACE);
        String type = authorization.substring(0, spaceIndex);
        if (!type.equals(VALIDATION_TYPE)) return false;

        String token = authorization.substring(spaceIndex + 1);
        log.info(token);

        if (!JWTUtil.verify(token)) return false;

        log.info(JWTUtil.getPhone(token));
        return true;
    }
}
