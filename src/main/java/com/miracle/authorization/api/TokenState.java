package com.miracle.authorization.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Administrator
 * Created at 2021/9/22 16:34
 **/
@Getter
@AllArgsConstructor
public enum TokenState {

    /**
     * 正常，有效
     */
    NORMAL("normal", "message.token_state_normal"),

    /**
     * 已被禁止访问
     */
    DENY("deny", "message.token_state_deny"),

    /**
     * 已过期
     */
    EXPIRED("expired", "message.token_state_expired"),


    /**
     * 强制下线
     */
    OFFLINE("offline", "message.token_state_offline"),

    /**
     * 锁定
     */
    LOCK("lock", "message.token_state_lock")

    ;

    private final String value;

    private final String text;

}
