package com.yang.server.common;

import com.yang.server.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    /**
     * ThreadLocal确保高并发下每个请求的request，response都是独立的
     */
    private static ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> currentResponse = new ThreadLocal<>();
    private static ThreadLocal<UserInfo> userinfo = new ThreadLocal<>();

    /**
     * 每次请求前先访问该方法，注入request和response对象
     *
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        currentRequest.set(request);
        currentResponse.set(response);
    }

    protected HttpServletRequest getRequest() {
        return currentRequest.get();
    }

    protected HttpServletResponse getResponse() {
        return currentResponse.get();
    }

    public static void setUserInfo(UserInfo user) {
        userinfo.set(user);
    }

    protected UserInfo getUserInfo() {
        return userinfo.get();
    }

    /**
     * 获得PageNum
     *
     * @return
     */
    protected int getPageNum() {
        int pageNum = 1;
        try {
            String pageNums = getRequest().getParameter("pageNum");
            if (pageNums != null && StringUtils.isNumeric(pageNums)) {
                pageNum = Integer.parseInt(pageNums);
            }
        } catch (NumberFormatException e) {
            // ignore
        }
        return pageNum;
    }
    /**
     * 获得请求路径
     * @author Yang Chunhai
     * @return
     */
    protected String getHeader() {
        return getRequest().getHeader("referer");
    }

    /**
     * 设置默认每页大小
     *
     * @return
     */
    protected int getPageSize() {
        // 默认每页20条记录
        int pageSize = 20;
        try {
            String pageSizes = getRequest().getParameter("pageSize");
            if (pageSizes != null && StringUtils.isNumeric(pageSizes)) {
                pageSize = Integer.parseInt(pageSizes);
            }
        } catch (NumberFormatException e) {
            // ignore
        }
        return pageSize;
    }
}
