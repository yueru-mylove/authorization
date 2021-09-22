package com.miracle.authorization.api;

import org.springframework.util.StringUtils;

/**
 * @author Administrator
 * Created at 2021/9/22 17:29
 **/
public class AuthenticationUtils {


    public static AuthenticationPredicate createPredicate(String expression) {
        if (!StringUtils.hasText(expression)) {
            return authentication -> false;
        }

        AuthenticationPredicate main = null;
        AuthenticationPredicate temp = null;

        boolean lastAnd = true;
        String[] configs = expression.split("[ ]");
        for (String config : configs) {
            if (config.startsWith("resource:") || config.startsWith("permission:")) {
                String[] permissionAndActions = config.split("[:]", 2);
                if (permissionAndActions.length < 2) {
                    temp = authentication -> !authentication.getPermissions().isEmpty();
                } else {
                    String[] real = permissionAndActions[1].split("[:]");
                    temp = real.length > 1 ?
                            AuthenticationPredicate.permission(real[0], real[1].split("[,]"))
                            : AuthenticationPredicate.permission(real[0]);
                }
            } else if (main != null && config.equalsIgnoreCase("and")) {
                lastAnd = true;
                main = main.and(temp);
            } else if (main != null && config.equalsIgnoreCase("or")) {
                lastAnd = false;
                main = main.or(temp);
            } else {
                String[] real = config.split("[:]", 2);
                if (real.length < 2) {
                    temp = AuthenticationPredicate.dimension(real[0]);
                } else {
                    temp = AuthenticationPredicate.dimension(real[0], real[1].split(","));
                }
            }

            if (main == null) {
                main = temp;
            }
        }


        return main == null ? a -> false : (lastAnd ? main.and(temp) : main.or(temp));
    }

}
