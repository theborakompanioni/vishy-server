package org.tbk.vishy.client.analytics;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import lombok.Value;
import lombok.experimental.Builder;

public interface AnalyticsScriptLoaderFactory {
    String createLoaderScript(ScriptConfig scriptConfig);

    @Value
    @Builder
    class ScriptConfig {
        private static int MAX_VALUE_LENGTH = 32;
        private static CharMatcher VALID_CHARS = CharMatcher.inRange('a', 'z')
                .or(CharMatcher.inRange('A', 'Z'))
                .or(CharMatcher.javaDigit())
                .or(CharMatcher.anyOf("-_"))
                .precomputed();

        private String elementId;
        private String projectId;
        private String experimentId;

        public boolean isValid() {
            return isValid(elementId) &&
                    isValid(projectId) &&
                    isValid(experimentId);
        }

        private boolean isValid(String value) {
            return !Strings.nullToEmpty(value).isEmpty() &&
                    value.length() < MAX_VALUE_LENGTH &&
                    VALID_CHARS.matchesAllOf(value);
        }
    }
}
