package com.miracle.authorization.api;


import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Administrator
 * Created at 2021/9/22 14:46
 **/
public interface AuthenticationSupplier extends Supplier<Optional<Authentication>> {



    Optional<Authentication> get(String userId);

}
