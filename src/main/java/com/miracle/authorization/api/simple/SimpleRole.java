package com.miracle.authorization.api.simple;

import com.miracle.authorization.api.Dimension;
import com.miracle.authorization.api.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Administrator
 * Created at 2021/9/22 11:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleRole implements Role {

    private String id;

    private String name;

    private Map<String, Object> options;


    public static Role of(Dimension dimension) {
        return new SimpleRole(dimension.getId(), dimension.getName(), dimension.getOptions());
    }

}
