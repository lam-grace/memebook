package com.purple.controller;

import com.purple.model.User;
import com.purple.security.AuthAspect;
import org.aspectj.lang.annotation.Aspect;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    private Map<String,String> errorMessages = new HashMap<>();

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public User getCurrentUserFromAttribute(HttpServletRequest request) {
        return (User)request.getAttribute(AuthAspect.USER_ATTRIBUTE);
    }

    public User getCurrentUserFromCookie(HttpServletRequest request) {
        return AuthAspect.getUserFromCookie(request);
    }

    public String getErrorString(){
        StringBuilder errors = new StringBuilder("");
        this.errorMessages.forEach((key, value) -> errors.append(value));
        return errors.toString();
    }


}
