package com.attendence.app.security;

import org.springframework.security.core.Authentication;

public class Helper {

    public static String getuserNameofLoginUser(Authentication authenticateAction){
       return authenticateAction.getName();
    }
}
