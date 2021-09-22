package com.miracle.authorization.api;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * @author Administrator
 * Created at 2021/9/22 17:30
 **/
@FunctionalInterface
public interface AuthenticationPredicate extends Predicate<Authentication> {


    static AuthenticationPredicate has(String permissionString) {
        return AuthenticationUtils.createPredicate(permissionString);
    }


    static AuthenticationPredicate dimension(String dimension, String... id) {
        return auth -> auth.hasDimension(dimension, Arrays.asList(id));
    }

    static AuthenticationPredicate permission(String permissionId, String... actions) {
        return auth -> auth.hasPermission(permissionId, actions);
    }

    default AuthenticationPredicate and(String permissionString) {
        return and(has(permissionString));
    }

    default AuthenticationPredicate or(String permissionString) {
        return or(has(permissionString));
    }

    @Override
    default AuthenticationPredicate and(Predicate<? super Authentication> other) {
        return t -> test(t) && other.test(t);
    }


    @Override
    default AuthenticationPredicate or(Predicate<? super Authentication> other) {
        return t -> test(t) || other.test(t);
    }
}
