package com.miracle.authorization.api;

import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * Created at 2021/9/22 14:16
 **/
public class ReactiveAuthenticationHolder {

    private static final List<ReactiveAuthenticationSupplier> SUPPLIERS = new CopyOnWriteArrayList<>();

    private static Mono<Authentication> get(Function<ReactiveAuthenticationSupplier, Mono<Authentication>> function) {
        return Flux.merge(SUPPLIERS.stream()
                                   .map(function)
                                   .collect(Collectors.toList()))
                   .collectList()
                   .filter(list -> !CollectionUtils.isEmpty(list))
                   .map(all -> {
                       if (all.size() == 1) {
                           return all.get(0);
                       }

                       SimpleAuthentication simpleAuthentication = new SimpleAuthentication();
                       for (Authentication authentication : all) {
                           simpleAuthentication.merge(authentication);
                       }

                       return simpleAuthentication;
                   });
    }


    public static Mono<Authentication> get() {
        return get(ReactiveAuthenticationSupplier::get);
    }


    public static Mono<Authentication> get(String userId) {
        return get(supplier -> supplier.get(userId));
    }


    public static void addSupplier(ReactiveAuthenticationSupplier supplier) {
        SUPPLIERS.add(supplier);
    }

    public static void setSupplier(ReactiveAuthenticationSupplier supplier) {
        SUPPLIERS.clear();
        SUPPLIERS.add(supplier);
    }


}
