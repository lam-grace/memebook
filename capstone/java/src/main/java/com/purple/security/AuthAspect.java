package com.purple.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.purple.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Aspect
@Configuration
public class AuthAspect {
    private static final String USER_COOKIE = ".Auth";
    public static final String USER_ATTRIBUTE = "User";
    private static final String NOT_AUTHORIZED = "Not authorized";
    @Around(value="@annotation(authorized) && args(request,response,..)")
    public Object around(ProceedingJoinPoint joinPoint,Authorized authorized,HttpServletRequest request,HttpServletResponse response) throws Throwable{
        if (request == null) {
            response.sendError(403);
            return null;
        }
        User user = getUserFromCookie(request);
        if ((user==null) || ((!authorized.requiredRole().equals("")) && (!user.isMemberOfRole(authorized.requiredRole())))) {
            response.sendError(403);
            return null;
        }
        request.setAttribute(USER_ATTRIBUTE,user);
        return joinPoint.proceed();
    }
    public static User getUserFromCookie(HttpServletRequest httpRequest){
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies == null){
            return null;
        }
        String userJson = null;
        for (Cookie cookie : httpRequest.getCookies()) {
            if (cookie.getName().equals(USER_COOKIE)) {
                userJson = new String(Base64.decode(cookie.getValue()));
                break;
            }
        }
        if (userJson==null) return null;

        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> map = springParser.parseMap(userJson);
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        User user = mapper.convertValue(map, User.class);

        return user;
    }
    public static Cookie getUserCookie(User user){
        ObjectMapper mapper = new ObjectMapper();
        String userJson=null;
        try {
            userJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Cookie(USER_COOKIE, Base64.toBase64String(userJson.getBytes()));
    }

    public static void setUserCookie(User user, HttpServletResponse response){
        response.addCookie(AuthAspect.getUserCookie(user));
    }

    public static void removeUserFromCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(USER_COOKIE)) {
                Cookie newCookie = new Cookie(cookie.getName(), "");
                newCookie.setMaxAge(0);
                response.addCookie(newCookie);
                return;
            }
        }
    }
}