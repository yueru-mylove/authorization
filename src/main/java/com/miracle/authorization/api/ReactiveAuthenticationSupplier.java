package com.miracle.authorization.api;

import reactor.core.publisher.Mono;

import java.util.function.Supplier;

/**
 * @author Administrator
 * Created at 2021/9/22 14:16
 **/
public interface ReactiveAuthenticationSupplier extends Supplier<Mono<Authentication>> {

    Mono<Authentication> get(String userId);
}
