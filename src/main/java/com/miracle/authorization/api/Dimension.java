package com.miracle.authorization.api;

import com.miracle.authorization.api.simple.SimpleDimension;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

/**
 * @author Administrator
 * Created at 2021/9/22 10:57
 **/
public interface Dimension extends Serializable {


    String getId();

    String getName();

    DimensionType getType();

    Map<String, Object> getOptions();


    default <T> Optional<T> getOption(String key) {
        return Optional.ofNullable(getOptions())
                       .map(opt -> opt.get(key))
                       .map(o -> (T) o);
    }


    default boolean typeIs(DimensionType type) {
        return this.getType() == type || this.getType().isSameType(type.getId());
    }


    default boolean typeIs(String type) {
        return this.getType().getId().equals(type);
    }

    static Dimension of(String id, String name, DimensionType type) {
        return of(id, name, type, null);
    }


    static Dimension of(String id, String name, DimensionType type, Map<String, Object> options) {
        return SimpleDimension.of(id, name, type, options);
    }
}
