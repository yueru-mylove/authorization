package com.miracle.authorization.api;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

/**
 * @author Administrator
 * Created at 2021/9/22 14:45
 **/
public final class AuthenticationHolder {

    private static final List<AuthenticationSupplier> SUPPLIERS = new ArrayList<>();
    private static final ReadWriteLock LOCk = new ReentrantReadWriteLock();

    static Optional<Authentication> get() {
        return get(AuthenticationSupplier::get);

    }


    private static Optional<Authentication> get(Function<AuthenticationSupplier, Optional<Authentication>> mapping) {
        return Flux.fromStream(SUPPLIERS.stream().map(mapping))
                   .filter(Optional::isPresent)
                   .map(Optional::get)
                   .reduceWith(SimpleAuthentication::new, SimpleAuthentication::merge)
                   .filter(auth -> auth.getUser() != null)
                   .map(Authentication.class::cast)
                   .blockOptional();
    }

    public static Optional<Authentication> get(String userId) {
        return get(supplier -> supplier.get(userId));
    }


    public static void addSupplier(AuthenticationSupplier supplier) {
        LOCk.writeLock().lock();
        try {
            SUPPLIERS.add(supplier);
        } finally {
            LOCk.writeLock().unlock();
        }
    }
}
