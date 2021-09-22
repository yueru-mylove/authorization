package com.miracle.authorization.api;

/**
 * @author Administrator
 * Created at 2021/9/22 10:58
 **/
public interface DimensionType {


    String getId();

    String getName();


    default boolean isSameType(DimensionType another) {
        return this == another || isSameType(another.getId());
    }


    default boolean isSameType(String anotherId) {
        return this.getId().equals(anotherId);
    }
}
