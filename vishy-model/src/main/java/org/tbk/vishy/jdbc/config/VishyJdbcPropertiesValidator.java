package org.tbk.vishy.jdbc.config;

import com.google.common.base.Strings;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.google.common.base.Preconditions.checkArgument;

public class VishyJdbcPropertiesValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return VishyJdbcProperties.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        checkArgument(supports(target.getClass()), "Unsupported type.");

        VishyJdbcProperties properties = (VishyJdbcProperties) target;

        if (properties.getJdbcUrl() == null || Strings.isNullOrEmpty(properties.getJdbcUrl())) {
            errors.rejectValue("jdbcUrl", "jdbcUrl.empty", "Empty jdbc url.");
        }
    }
}
