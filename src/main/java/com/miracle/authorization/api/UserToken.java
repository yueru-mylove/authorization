package com.miracle.authorization.api;

import java.io.Serializable;

/**
 * @author Administrator
 * Created at 2021/9/22 16:33
 **/
public interface UserToken extends Serializable, Comparable<UserToken> {


    String getUserId();

    String getToken();


    long getRequestTime();


    long getLastRequestTime();


    long getSignInTime();

    TokenState getState();


    String getType();

    /**
     * 会话过期时间， 毫秒
     *
     * @return  会话过期时间
     */
    long getNextInactiveInterval();


    /**
     * 检查会话是否过期
     *
     * @return  是否过期
     */
    default boolean checkExpired() {
        long nextInactiveInterval = getNextInactiveInterval();
        if (nextInactiveInterval > 0) {
            return System.currentTimeMillis() - getLastRequestTime() > nextInactiveInterval;
        }

        return false;
    }


    default boolean isNormal() {
        return this.getState() == TokenState.NORMAL;
    }

    default boolean isExpired() {
        return getState() == TokenState.EXPIRED;
    }

    default boolean isDeny() {
        return getState() == TokenState.DENY;
    }

    default boolean isOffline() {
        return getState() == TokenState.OFFLINE;
    }


    default boolean isLock() {
        return getState() == TokenState.LOCK;
    }


    default boolean validate() {
        if (!isNormal()) {
            throw new RuntimeException(getState().toString());
        }

        return true;
    }

    @Override
    default int compareTo(UserToken o) {
        if (o == null) {
            return 0;
        }

        return Long.compare(getSignInTime(), o.getSignInTime());
    }
}
