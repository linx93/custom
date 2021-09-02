package com.tianji.chain.interceptor;

import com.tianji.chain.model.bo.AppBO;
import com.tianji.chain.service.CheckReqService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 检查请求的合法性
 * @author: xionglin
 * @create: 2021-09-01 18:50
 */
public class CheckReq implements HandlerInterceptor {
    private final CheckReqService checkReqService;

    public CheckReq(CheckReqService checkReqService) {
        this.checkReqService = checkReqService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = request.getHeader("x-key");
        String rand = request.getHeader("x-rand");
        String signature = request.getHeader("x-signature");
        AppBO appBO = new AppBO();
        appBO.setAppId(key);
        appBO.setRand(rand);
        appBO.setSignature(signature);
        boolean check = checkReqService.check(appBO);
        return check;
    }
}
