package com.yang.server.controller;


import com.alibaba.fastjson.JSONObject;
import com.yang.server.common.BaseController;
import com.yang.server.common.utils.GetIpUtil;
import com.yang.server.common.utils.TokenUtil;
import com.yang.server.entity.UserInfo;
import com.yang.server.common.handler.ResultHandler;
import com.yang.server.service.UserInfoService;
import com.yang.server.vo.TokenPayLoad;
import com.yang.server.vo.request.UserInfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@RestController
@RequestMapping(value = "/login")
public class LoginController extends BaseController {

    final static Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserInfoService loginService;

    @PostMapping(value = "/dologin")
    public ResultHandler<String> getLoginInfo(@RequestBody UserInfoRequest userInfoVo,
                                              HttpServletResponse response, HttpServletRequest request) {
        try {
            String password = DigestUtils.md5DigestAsHex(userInfoVo.getPassword().getBytes());
            UserInfo user = loginService.verifierUser(userInfoVo.getUsername(), password);
            if (user == null) {
                return ResultHandler.error("用户名或密码不正确", "0");
            }
            //获取是否保存Cookie
            if(userInfoVo.isRemember()) {
                //创建Cookie
                Cookie nameCookie = new Cookie("name", URLEncoder.encode(userInfoVo.getUsername(),"utf-8"));
                Cookie pwCookie = new Cookie("psw",userInfoVo.getPassword());

                //设置Cookie的路径
                nameCookie.setPath("/");
                pwCookie.setPath("/");

                nameCookie.setMaxAge(7*24*60*60);
                pwCookie.setMaxAge(7*24*60*60);

                //加入Cookie到响应头
                response.addCookie(nameCookie);
                response.addCookie(pwCookie);
            } else {
                // 未勾选记住密码则清除cookie
                Cookie nameCookie = new Cookie("name", null);
                Cookie pwCookie = new Cookie("psw", null);

                //设置Cookie的路径
                nameCookie.setPath("/");
                pwCookie.setPath("/");

                nameCookie.setMaxAge(0);
                pwCookie.setMaxAge(0);

                //加入Cookie到响应头
                response.addCookie(nameCookie);
                response.addCookie(pwCookie);
            }

            String payload = JSONObject.toJSONString(new TokenPayLoad(user.getUserId(),
                    user.getUsername(), user.getPassword(), GetIpUtil.getUserIp(request)));
            String token = TokenUtil.getToken(payload);
            return ResultHandler.success(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ResultHandler.error("登陆失败", null);
    }

    @PostMapping(value = "/logout")
    public ResultHandler<?> logout(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(HttpHeaders.AUTHORIZATION, "");
        return ResultHandler.success(null);
    }
}
