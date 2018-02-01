//package com.ipinyou.demo;
//
//import javax.servlet.http.*;
//import java.util.HashMap;
//import java.util.Map;
//
//public class CookiesUtil{
//    public Map<String, Cookie> readCookieMap(HttpServletRequest request) {
//        Map<String, Cookie> cookieMap = new HashMap<>();
//        Cookie[] cookies = request.getCookies();
//        if (null != cookies) {
//            for (Cookie cookie : cookies) {
//                cookieMap.put(cookie.getName(), cookie);
//            }
//        }
//        return cookieMap;
//    }
//
//}
