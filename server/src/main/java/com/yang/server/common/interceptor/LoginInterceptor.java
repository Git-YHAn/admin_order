package com.yang.server.common.interceptor;

import com.yang.server.common.BaseController;
import com.yang.server.entity.UserInfo;
import com.yang.server.service.UserInfoService;
import com.yang.server.common.utils.TokenUtil;
import com.yang.server.vo.TokenPayLoad;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 静态资源直接return true
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        // options请求跳过、登入登出跳过
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())
                || request.getRequestURI().toString().matches("/login/.*")) {
            response.setHeader("Access-Control-Allow-Origin", request.getHeader(HttpHeaders.ORIGIN));
            response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers",
                    "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            return true;
        }
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        TokenPayLoad payLoad = null;
        if (StringUtils.isNotBlank(token)) {
            payLoad = TokenUtil.parseToken(token);
            if (payLoad == null) {
                token = TokenUtil.refreshToken(token);
                payLoad = TokenUtil.parseToken(token);
            }
        }

        if (payLoad == null || !request.getRemoteHost().equals(payLoad.getIp())) {
            logger.info("用户未登陆");
			return false;
        } else {
            response.setHeader("Access-Control-Expose-Headers", HttpHeaders.AUTHORIZATION);
            response.setHeader(HttpHeaders.AUTHORIZATION, token);
            Long userid = payLoad.getUserid();
            UserInfo user = userInfoService.getUserById(userid);
            BaseController.setUserInfo(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
