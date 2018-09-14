package de.seven.converter.model.rule;

import de.seven.converter.json.Object2JsonConverter;
import de.seven.fate.model.builder.annotation.Inject;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NumericGreaterThanEqualsRuleDefinitionTest {

    @Inject
    NumericGreaterThanEqualsRuleDefinition sut;

    @Inject
    Object2JsonConverter object2JsonConverter;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void dsl() {

        sut.setComparison(2);
        sut.setVariable("$.size");
        sut.setNext("SmallSizeState");

        String expected = "{\n" +
                "  \"Variable\" : \"$.size\",\n" +
                "  \"NumericGreaterThanEquals\" : 2,\n" +
                "  \"Next\" : \"SmallSizeState\"\n" +
                "}";

        assertEquals(expected, object2JsonConverter.convert(sut));
    }
}