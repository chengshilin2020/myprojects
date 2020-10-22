package com.example.demo.controller;

import com.example.demo.conmmon.TokenUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class HelloController {

    private static String MYtoken="";

    @GetMapping("hello")
    public String hello(String name){
        return "hello myGit"+"-------"+name;
    }
    
    @GetMapping("setCookie")
    public  String setCookies(HttpServletResponse response){
        //HttpServerletRequest 装请求信息类
        //HttpServerletRespionse 装相应信息的类
        Cookie cookie=new Cookie("myCookie","CookieTestInfo");
        response.addCookie(cookie);
        return "添加cookies信息成功";
    }

    @GetMapping("getCookie")
    public  String getCookies(HttpServletRequest request){
        //HttpServletRequest 装请求信息类
        //HttpServletRespionse 装相应信息的类
        //   Cookie cookie=new Cookie("sessionId","CookieTestInfo");
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("myCookie")){
                    return cookie.getValue();
                }
            }
        }
        return  null;
    }
    @RequestMapping("/testCookieValue")
    public String testCookieValue(@CookieValue("myCookie") String sessionId ) {
        //前提是已经创建了或者已经存在cookie了，那么下面这个就直接把对应的key值拿出来了。
        System.out.println("testCookieValue,myCookie="+sessionId);
        return "SUCCESS";
    }

    @GetMapping("/setSession")
    public String setSession(HttpSession session) {
        session.setAttribute("user", "javaboy");
        return "添加session信息成功";
    }

    @GetMapping("/getSession")
    public String getSession(HttpSession session) {
        return session.getAttribute("user").toString();
    }

    @GetMapping("/setToken")
    public String setToken(String username,String password) {
        String token=TokenUtils.token(username,password);
        MYtoken=token;
        return token;
    }

    @GetMapping("/getToken")
    public String getToken(HttpSession session) {
        boolean b=TokenUtils.verify(MYtoken);
        return b==true?"success":"false";
    }
}
