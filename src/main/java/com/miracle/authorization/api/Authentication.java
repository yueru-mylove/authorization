package com.miracle.authorization.api;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.io.Serializable;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * Created at 2021/9/22 14:14
 **/
public interface Authentication {


    /**
     * 当前登录用户的权限信息
     *
     * @return 当前用户权限信息
     */
    static Mono<Authentication> currentReactive() {
        return ReactiveAuthenticationHolder.get();
    }


    static Optional<Authentication> current() {
        return AuthenticationHolder.get();
    }


    User getUser();


    List<Permission> getPermissions();


    List<Dimension> getDimensions();


    default boolean hasDimension(String type, String... ids) {
        return hasDimension(type, Arrays.asList(ids));
    }


    default boolean hasDimension(String type, Collection<String> ids) {
        if (ids.isEmpty()) {
            return !getDimensions(type).isEmpty();
        }

        return getDimensions(type)
                .stream()
                .anyMatch(dimension -> ids.contains(dimension.getId()));
     }


    default boolean hasDimension(DimensionType type, String id) {
        return getDimension(type, id).isPresent();
    }



    default Optional<Dimension> getDimension(DimensionType type, String id) {
        if (type == null) {
            return Optional.empty();
        }

        return getDimensions()
                .stream()
                .filter(dimension -> dimension.getType().isSameType(type) && dimension.getId().equals(id))
                .findFirst();
    }



    default Optional<Dimension> getDimension(String type, String id) {
        if (StringUtils.hasText(type)) {
            return Optional.empty();
        }

        return getDimensions()
                .stream()
                .filter(dimension -> dimension.getType().isSameType(type) && dimension.getId().equals(id))
                .findFirst();
    }


    default List<Dimension> getDimensions(String type) {
        return getDimensions()
                .stream()
                .filter(dimension -> dimension.getType().isSameType(type))
                .collect(Collectors.toList());
    }


    default List<Dimension> getDimensions(DimensionType type) {
        return getDimensions()
                .stream()
                .filter(dimension -> dimension.getType().isSameType(type))
                .collect(Collectors.toList());
    }



    default boolean hasPermission(String permissionId, String... actions) {
        return hasPermission(permissionId, Arrays.asList(actions));
    }


    default boolean hasPermission(String permissionId, Collection<String> actions) {
        return CollectionUtils.isEmpty(actions) ||
                getPermission(permissionId)
                        .filter(permission -> permission.getActions().containsAll(actions))
                        .isPresent();

    }

    default Optional<Permission> getPermission(String permissionId) {
        if (permissionId == null) {
            return Optional.empty();
        }

        return getPermissions()
                .stream()
                .filter(permission -> permission.getId().equals(permissionId))
                .findAny();
    }


    /**
     * 根据属性名获取其中的一个属性
     *
     * @param name 属性名
     * @param <T>  属性信息
     * @return 属性值
     */
    <T extends Serializable> Optional<T> getAttribute(String name);


    /**
     * 属性集合
     *
     * @return 全部属性集合
     */
    Map<String, Serializable> getAttributes();


    /**
     * 合并权限
     *
     * @param source 源权限
     * @return 合并后的权限信息
     */
    Authentication merge(Authentication source);


    Authentication copy(BiPredicate<Permission, String> permissionFilter, Predicate<Dimension> dimensionPredicate);
}
