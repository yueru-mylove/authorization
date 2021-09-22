package com.miracle.authorization.api;

import java.util.Optional;

/**
 * @author Administrator
 * Created at 2021/9/22 15:31
 **/
public interface AuthenticationManager {

    /**
     * 根据用户id获取权限信息
     *
     * @param userId    用户id
     * @return  用户权限信息
     */
    Optional<Authentication> getByUserId(String userId);


    Authentication authenticate(AuthenticationRequest request);
}
