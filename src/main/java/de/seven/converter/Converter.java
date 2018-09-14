package de.seven.converter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static de.seven.converter.bpmn.constants.Constants.MAY_NOT_BE_NULL;

/**
 * Strategy converter from source to target
 *
 * @param <S> Source generic type
 * @param <T> Target generic type
 */
public interface Converter<S, T> {

    /**
     * @param source may not be null
     * @return new Instance of T or null
     */
    default T convert(S source) {
        if (source == null) {
            return null;
        }

        T target = createInstance();

        update(source, target);

        return target;
    }

    /**
     * @param source may not be null
     * @param target may not be null
     */
    default void update(S source, T target) {
        //Validate.notNull(source, MAY_NOT_BE_NULL, "source");
        //Validate.notNull(target, MAY_NOT_BE_NULL, "target");

        //copy properties from source to target
    }

    /**
     * @return new Instance of target type
     */
    default T createInstance() {

        return null;
    }

    default Class<T> getTargetType() {
        return null;
    }

    default Class<S> getSourceType() {
        return null;
    }

    /**
     * @param sources may be null or empty
     * @return immutable List never null
     */
    default List<T> convertToList(Collection<S> sources) {

        if (CollectionUtils.isEmpty(sources)) {

            return Collections.emptyList();
        }

        return sources.stream().map(this::convert)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    /**
     * @param sources may be null or empty
     * @return immutable List never null
     */
    default Set<T> convertToSet(Collection<S> sources) {

        if (CollectionUtils.isEmpty(sources)) {

            return Collections.emptySet();
        }

        return sources.stream().map(this::convert)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }
}
