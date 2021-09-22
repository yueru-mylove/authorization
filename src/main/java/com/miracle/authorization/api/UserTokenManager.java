package com.miracle.authorization.api;

import reactor.core.publisher.Mono;

/**
 * @author Administrator
 * Created at 2021/9/22 15:32
 **/
public interface UserTokenManager {

    Mono<UserToken> getByToken(String token);

}
