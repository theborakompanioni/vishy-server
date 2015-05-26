package org.tbk.vishy.core.client.segmentio;

import com.google.common.base.Strings;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class SegmentIoCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Strings.nullToEmpty(context.getEnvironment().getProperty("VISHY_CLIENT")).equalsIgnoreCase("segmentio");
    }
}