package com.leien.config;

import com.leien.dao.UserDao;
import com.leien.entity.User;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/20 10:17
 * @Description:
 */
@Component
public class MyInterceptorConfig implements HandlerInterceptor {

    @Resource
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session =  request.getSession();
        User user = (User) session.getAttribute("user");
        if (user!=null){
            //已登陆，放行请求
            return true;
        }else {
            //未登陆，返回登陆页面
//            response.setCharacterEncoding("utf-8");
//            response.sendError(403,"没有权限");
//            request.setAttribute("msg","没有权限请先登陆");
            //获取转发器，转发请求到视图映射器，登录页面
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
