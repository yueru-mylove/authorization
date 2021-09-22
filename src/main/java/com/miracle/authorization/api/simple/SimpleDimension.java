package com.miracle.authorization.api.simple;

import com.miracle.authorization.api.Dimension;
import com.miracle.authorization.api.DimensionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Administrator
 * Created at 2021/9/22 11:02
 **/
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class SimpleDimension implements Dimension {

    private String id;

    private String name;

    private DimensionType type;

    private Map<String, Object> options;

}
