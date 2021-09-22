package com.miracle.authorization.api.access;

import java.util.Set;

/**
 * @author Administrator
 * Created at 2021/9/22 14:01
 **/
public interface ScopeDataAccessConfig extends DataAccessConfig {

    String getScopeType();



    Set<Object> getScope();

}
