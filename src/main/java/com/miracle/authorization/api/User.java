package com.miracle.authorization.api;

/**
 * @author Administrator
 * Created at 2021/9/22 11:05
 **/
public interface User extends Dimension {

    String getUsername();

    String getUserType();


    @Override
    default DimensionType getType() {
        return DefaultDimensionType.USER;
    }
}
