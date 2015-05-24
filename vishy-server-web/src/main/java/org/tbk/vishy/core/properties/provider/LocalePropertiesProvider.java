package org.tbk.vishy.core.properties.provider;

import com.google.common.collect.ImmutableMap;
import org.tbk.openmrc.core.properties.provider.PropertyProvider;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by void on 02.05.15.
 */
public class LocalePropertiesProvider implements PropertyProvider {

    private static Function<Locale, Map<String, Object>> LOCALE_AS_MAP = (locale) ->
        ImmutableMap.<String, Object>builder()
            .put("locale", ImmutableMap.<String, Object>builder()
                .put("locale", locale.toString())
                .put("country", locale.getCountry())
                .put("language", locale.getLanguage())
                .put("language_tag", locale.getLanguage())
                .put("display_country", locale.getDisplayCountry())
                .put("display_language", locale.getDisplayLanguage())
                .put("display_name", locale.getDisplayName())
                .build())
            .build();

    private final Locale locale;

    public LocalePropertiesProvider(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Optional<Map<String, Object>> get() {
        return Optional.ofNullable(locale)
            .map(LOCALE_AS_MAP);
    }
}
