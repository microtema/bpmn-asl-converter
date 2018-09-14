package de.seven.converter.model.rule;

import de.seven.converter.json.Object2JsonConverter;
import de.seven.fate.model.builder.annotation.Inject;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimestampGreaterThanEqualsRuleDefinitionTest {

    @Inject
    TimestampGreaterThanEqualsRuleDefinition sut;

    @Inject
    Object2JsonConverter object2JsonConverter;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void dsl() {

        sut.setComparison("2001-01-01T12:00:00Z");
        sut.setVariable("$.Timestamp");
        sut.setNext("NextState");

        String expected = "{\n" +
                "  \"Variable\" : \"$.Timestamp\",\n" +
                "  \"TimestampGreaterThanEquals\" : \"2001-01-01T12:00:00Z\",\n" +
                "  \"Next\" : \"NextState\"\n" +
                "}";

        assertEquals(expected, object2JsonConverter.convert(sut));
    }
}