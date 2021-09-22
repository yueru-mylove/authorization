package com.miracle.authorization.api;

import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * Created at 2021/9/22 14:24
 **/
@Data
public class SimpleAuthentication implements Authentication {

    private User user;
    private List<Permission> permissions = new ArrayList<>();
    private List<Dimension> dimensions = new ArrayList<>();

    private Map<String, Serializable> attributes = new HashMap<>();

    public static Authentication of() {
        return new SimpleAuthentication();
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T extends Serializable> Optional<T> getAttribute(String name) {
        return Optional.ofNullable((T) attributes.get(name));
    }

    @Override
    public Map<String, Serializable> getAttributes() {
        return attributes;
    }

    @Override
    public SimpleAuthentication merge(Authentication authentication) {
        Map<String, Permission> permissionGroup = permissions.stream()
                                                             .collect(Collectors.toMap(Permission::getId, Function.identity()));

        user = authentication.getUser();
        attributes.putAll(authentication.getAttributes());
        for (Permission permission : authentication.getPermissions()) {
            Permission me = permissionGroup.get(permission.getId());
            if (me == null) {
                permissions.add(permission.copy());
                continue;
            }
            me.getActions().addAll(permission.getActions());
            me.getDataAccesses().addAll(permission.getDataAccesses());
        }

        for (Dimension dimension : authentication.getDimensions()) {
            if (!getDimension(dimension.getType(), dimension.getId()).isPresent()) {
                dimensions.add(dimension);
            }
        }

        return this;
    }

    @Override
    public Authentication copy(BiPredicate<Permission, String> permissionFilter, Predicate<Dimension> dimensionPredicate) {
        SimpleAuthentication authentication = new SimpleAuthentication();
        authentication.setUser(user);
        authentication.setDimensions(dimensions.stream().filter(dimensionPredicate).collect(Collectors.toList()));
        authentication.setPermissions(permissions
                                              .stream()
                                              .map(permission -> permission.copy(action -> permissionFilter.test(permission, action), conf -> true))
                                              .filter(permission -> !permission.getActions().isEmpty())
                                              .collect(Collectors.toList())
        );

        return authentication;
    }

}
