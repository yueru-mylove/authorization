package com.miracle.authorization.api.access;

import java.util.Set;

/**
 * 对字段进行过滤操作配置，实现字段级别的权限控制
 *
 * @author Administrator
 * Created at 2021/9/22 11:49
 **/
public interface FieldFilterDataAccessConfig extends DataAccessConfig {

    Set<String> getFields();
}
