package work.yjoker.homeworkhelper.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import work.yjoker.homeworkhelper.common.util.JWTUtil;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.util.Holder;
import work.yjoker.homeworkhelper.util.IPUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static work.yjoker.homeworkhelper.constant.ApiResultConstants.NOT_LOGIN;
import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 * @author HeYunjia
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 验证用户是否登录
     */
    @Override
    @SuppressWarnings("all")  // Not annotated parameter overrides @NonNullApi parameter
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) return true;

        String authorization = request.getHeader(Header.AUTHORIZATION.toString());

        String token = JWTUtil.verifyAuthorization(authorization);
        if (StrUtil.isEmpty(token)) {
            log.info("{} 请求 {} 被拦截", IPUtils.getIpAddress(request), request.getRequestURI());
            response.getWriter().write(JSONUtil.toJsonStr(ApiResult.fail(NOT_LOGIN)));
            return false;
        }

        String phone = JWTUtil.getPhone(token);
        log.info("请求数据的手机号: {}", phone);

        Holder.set(PHONE_HOLDER, phone);
        return true;
    }
}
