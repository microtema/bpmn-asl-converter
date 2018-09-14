package de.seven.converter;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Strategy converter from source to target with context
 *
 * @param <S> Source generic type
 * @param <T> Target generic type
 * @param <C> Context generic type
 */
public interface ExtendedConverter<S, T, C> extends Converter<S, T> {

    /**
     * @param source  may not be null
     * @param context may be null
     * @return target or null
     */
    default T convert(S source, C context) {
        if (source == null) {
            return null;
        }

        T target = createInstance();

        update(source, target, context);

        return target;
    }

    /**
     * @param source  may not be null
     * @param target  may not be null
     * @param context may be null or empty
     */
    default void update(S source, T target, C context) {
        update(source, target);
    }

    /**
     * @param sources may be null or empty
     * @param context may be null or empty
     * @return unmodifiable List never null
     */
    default List<T> convertToList(Collection<S> sources, C context) {

        if (CollectionUtils.isEmpty(sources)) {

            return Collections.emptyList();
        }

        return sources.stream().map(it -> convert(it, context))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    /**
     * @param sources may not be null
     * @param context may not be null
     * @return unmodifiable Set never null
     */
    default Set<T> convertToSet(Collection<S> sources, C context) {

        if (CollectionUtils.isEmpty(sources)) {

            return Collections.emptySet();
        }

        return sources.stream().map(it -> convert(it, context))
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }
}
