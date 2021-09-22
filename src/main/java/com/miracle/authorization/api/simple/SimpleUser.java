package com.miracle.authorization.api.simple;

import com.miracle.authorization.api.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Administrator
 * Created at 2021/9/22 11:13
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUser implements User {

    private String id;

    private String name;

    private String username;

    private String userType;

    private Map<String, Object> options;
}
