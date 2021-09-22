package com.miracle.authorization.api.access;

import java.io.Serializable;

/**
 * @author Administrator
 * Created at 2021/9/22 11:22
 **/
public interface DataAccessConfig extends Serializable {


    /**
     * 对数据的操作事件
     *
     * @return  操作事件
     */
    String getAction();

    /**
     * 控制方式标识
     *
     * @return  控制方式
     */
    DataAccessType getType();


    /**
     * 内置控制方式
     */
    interface DefaultType {

        String OWN_CREATED = "OWN_CREATED";


        String DENY_FIELDS = "DENY_FIELDS";


        String DIMENSION_SCOPE = "DIMENSION_SCOPE";

    }

}
