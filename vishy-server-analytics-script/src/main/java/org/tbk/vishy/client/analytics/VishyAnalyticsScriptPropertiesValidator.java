package org.tbk.vishy.client.analytics;

import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Ints;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Set;

public class VishyAnalyticsScriptPropertiesValidator implements Validator {

    private static Set<String> supportedProtocols = ImmutableSet.<String>builder()
            .add("http", "https").build();

    @Override
    public boolean supports(Class<?> clazz) {
        return VishyAnalyticsScriptProperties.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "host", "host.empty");
        ValidationUtils.rejectIfEmpty(errors, "port", "port.empty");

        VishyAnalyticsScriptProperties properties = (VishyAnalyticsScriptProperties) target;

        if (Ints.tryParse(properties.getPort(), 10) == null) {
            errors.rejectValue("port", "port.invalid", "Invalid port");
        }

        if (!supportedProtocols.contains(properties.getProtocol())) {
            errors.rejectValue("protocol", "protocol.invalid", "Unsupported protocol");
        }
    }
}
