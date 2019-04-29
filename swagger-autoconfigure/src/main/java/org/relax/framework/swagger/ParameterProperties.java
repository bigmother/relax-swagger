package org.relax.framework.swagger;

import lombok.Data;

@Data
public class ParameterProperties {
    private String name;
    private String description;
    private String modelRef;
    private String parameterType;
    private boolean required = false;
}
