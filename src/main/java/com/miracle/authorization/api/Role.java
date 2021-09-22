package com.miracle.authorization.api;

import com.miracle.authorization.api.simple.SimpleRole;

/**
 * @author Administrator
 * Created at 2021/9/22 11:09
 **/
public interface Role extends Dimension {

    @Override
    default DimensionType getType() {
        return DefaultDimensionType.ROLE;
    }


    static Role fromDimension(Dimension dimension) {
        return SimpleRole.of(dimension);
    }
}
