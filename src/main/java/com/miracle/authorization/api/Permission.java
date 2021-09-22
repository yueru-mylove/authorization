package com.miracle.authorization.api;

import com.miracle.authorization.api.access.DataAccessConfig;
import com.miracle.authorization.api.access.FieldFilterDataAccessConfig;
import com.miracle.authorization.api.access.ScopeDataAccessConfig;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * Created at 2021/9/22 11:16
 **/
@SuppressWarnings("all")
public interface Permission extends Serializable {


    /**
     * 查询
     */
    String ACTION_QUERY = "query";

    /**
     * 获取明细
     */
    String ACTION_GET = "get";

    /**
     * 新增
     */
    String ACTION_ADD = "add";

    /**
     * 保存
     */
    String ACTION_SAVE = "save";

    /**
     * 更新
     */
    String ACTION_UPDATE = "update";

    /**
     * 删除
     */
    String ACTION_DELETE = "delete";

    /**
     * 导入
     */
    String ACTION_IMPORT = "import";

    /**
     * 导出
     */
    String ACTION_EXPORT = "export";

    /**
     * 禁用
     */
    String ACTION_DISABLE = "disable";

    /**
     * 启用
     */
    String ACTION_ENABLE = "enable";


    String getId();


    String getName();

    /**
     * 拓展字段
     *
     * @return  拓展字段
     */
    Map<String, Object> getOptions();

    /**
     * 用户对此权限的可操作事件
     * 任何时候都不 应该对返回的set进行写操作
     *
     * @return  如果未配置返回空
     */
    Set<String> getActions();


    /**
     * 用户对此权限持有的数据权限信息， 用户数据级别的控制
     *
     * @return  未配置返回空集合
     */
    Set<DataAccessConfig> getDataAccesses();


    default Set<DataAccessConfig> getDataAccesses(String action) {
        return getDataAccesses()
                .stream()
                .filter(config -> config.getAction().equals(action))
                .collect(Collectors.toSet());
    }



    default <T extends DataAccessConfig> Optional<T> findDataAccess(DataAccessPredicate<T> predicate) {
        return (Optional<T>) getDataAccesses()
                .stream()
                .filter(predicate)
                .findFirst();
    }


    default Optional<FieldFilterDataAccessConfig> findFieldFIlter(String action) {
        return findDataAccess(conf -> conf instanceof FieldFilterDataAccessConfig && conf.getAction().equals(action));
    }


    default Set<String> findDenyFields(String action) {
        return findFieldFIlter(action)
                .filter(conf -> DataAccessConfig.DefaultType.DENY_FIELDS.equals(conf.getType().getId()))
                .map(FieldFilterDataAccessConfig::getFields)
                .orElseGet(() -> Collections.emptySet());
    }

    default Set<Object> findScope(String action, String type, String scopeType) {
        return findScope(scope(action, type, scopeType));
    }


    default Set<Object> findScope(DataAccessPredicate<ScopeDataAccessConfig> predicate) {
        return findDataAccess(predicate)
                .map(ScopeDataAccessConfig::getScope)
                .orElseGet(() -> Collections.emptySet());
    }


    static DataAccessPredicate<ScopeDataAccessConfig> scope(String action, String type, String scopeType) {
        return config -> config instanceof ScopeDataAccessConfig
                && config.getAction().equals(action)
                && config.getType().equals(type)
                && ((ScopeDataAccessConfig) config).getScopeType().equals(scopeType);
    }


    Permission copy();


    Permission copy(Predicate<String> actionFilter, Predicate<DataAccessConfig> dataAccessFilter);


    interface DataAccessPredicate<T extends DataAccessConfig> extends Predicate<DataAccessConfig> {

        @Override
        boolean test(DataAccessConfig dataAccessConfig);

        @Override
        default Predicate<DataAccessConfig> and(Predicate<? super DataAccessConfig> other) {
            return t -> test(t) && other.test(t);
        }


        @Override
        default Predicate<DataAccessConfig> or(Predicate<? super DataAccessConfig> other) {
            return t -> test(t) || other.test(t);
        }
    }
}

