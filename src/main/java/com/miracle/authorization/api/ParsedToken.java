package com.miracle.authorization.api;

/**
 * @author Administrator
 * Created at 2021/9/22 16:30
 **/
public interface ParsedToken {

    String getToken();


    String getType();


    static ParsedToken of(String type, String token) {
        return SimpleParsedToken.of(type, token);
    }
}
