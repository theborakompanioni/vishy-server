package org.tbk.vishy.utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by void on 14.03.15.
 */
public final class Optionals {
    public static <T> Optional<T> consume(Optional<T> source, Consumer<T> consumer) {
        source.ifPresent(consumer);
        return source;
    }

    public static <T> Supplier<Optional<T>> supplier(Supplier<T> supplier) {
        return () -> Optional.ofNullable(supplier)
            .map(Supplier::get);
    }

    public static <T> Supplier<Optional<T>> supplier() {
        return () -> Optional.empty();
    }
}
