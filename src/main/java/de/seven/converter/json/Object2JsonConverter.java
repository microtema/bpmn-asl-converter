package de.seven.converter.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import de.seven.converter.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * Object2JsonConverter provides functionality for writing JSON,
 * either to and from basic POJOs (Plain Old Java Objects), or to and from
 * a general-purpose JSON Tree Model ({@link JsonNode}), as well as
 * related functionality for performing conversions.
 * It is also highly customizable to work both with different styles of JSON
 * content, and to support more advanced Object concepts such as
 * polymorphism and Object identity.
 */
public class Object2JsonConverter implements Converter<Object, String> {

    private final ObjectMapper mapper;

    public Object2JsonConverter() {
        this.mapper = new ObjectMapper();

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new ISO8601DateFormat());
    }

    @Override
    public String convert(Object orig) {
        if (orig == null) {
            return null;
        }

        try {

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orig).replaceAll("\\r", StringUtils.EMPTY);

        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to write value[" + orig + "] as String", e);
        }
    }
}