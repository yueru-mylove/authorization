package com.miracle.authorization.api;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Administrator
 * Created at 2021/9/22 16:31
 **/
@Data
@AllArgsConstructor
public class SimpleParsedToken implements ParsedToken {

    private String token;

    private String type;

    public static SimpleParsedToken of(String token, String type) {
        return new SimpleParsedToken(token, type);
    }
}
