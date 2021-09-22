package com.miracle.authorization.api;

import java.util.Optional;

/**
 * @author Administrator
 * Created at 2021/9/22 16:01
 **/
public interface ThirdPartyAuthenticationManager {


    /**
     * 支持的token类型
     *
     * @return  支持的鉴权类型token类型
     */
    String getTokenType();


    /**
     * 根据用户id获取权限信息
     *
     * @param userId    用户id
     * @return  权限信息
     */
    Optional<Authentication> getByUserId(String userId);


}
