package com.miracle.authorization.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Administrator
 * Created at 2021/9/22 11:06
 **/
@Getter
@AllArgsConstructor
public enum DefaultDimensionType implements DimensionType {


    USER("用户"),

    ROLE("角色"),

    ;


    private final String name;


    @Override
    public String getId() {
        return name();
    }
}
