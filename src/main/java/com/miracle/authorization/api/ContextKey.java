package com.miracle.authorization.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Administrator
 * Created at 2021/9/22 15:37
 **/
@Getter
@AllArgsConstructor
public class ContextKey<T> {

    private final String key;

    public static <T> ContextKey<T> of(String key) {
        return new ContextKey<>(key);
    }

    public static <T> ContextKey<T> of(Class<T> key) {
        return of(key.getName());
    }


    public static ContextKey<String> string(String key) {
        return of(key);
    }

    public static ContextKey<Integer> integer(String key) {
        return of(key);
    }

    public static ContextKey<Boolean> bool(String key) {
        return of(key);
    }


}
