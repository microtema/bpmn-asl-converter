package de.seven.converter.model;

import de.seven.converter.enums.StateType;
import de.seven.converter.json.Object2JsonConverter;
import de.seven.converter.model.rule.NumericEqualsRuleDefinition;
import de.seven.fate.model.builder.annotation.Inject;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class ChoiceStateDefinitionTest {

    @Inject
    ChoiceStateDefinition sut;

    @Inject
    Object2JsonConverter object2JsonConverter;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void taskType() {

        assertSame(StateType.CHOICE, sut.getType());
    }

    @Test
    public void dslWithNumericEqualsRule() {

        NumericEqualsRuleDefinition ruleDefinition = new NumericEqualsRuleDefinition();
        ruleDefinition.setComparison(2);
        ruleDefinition.setVariable("$.size");
        ruleDefinition.setNext("SmallSizeState");

        sut.setChoices(Collections.singletonList(ruleDefinition));

        String expected = "{\n" +
                "  \"Type\" : \"Choice\",\n" +
                "  \"Choices\" : [ {\n" +
                "    \"Variable\" : \"$.size\",\n" +
                "    \"NumericEquals\" : 2,\n" +
                "    \"Next\" : \"SmallSizeState\"\n" +
                "  } ]\n" +
                "}";

        assertEquals(expected, object2JsonConverter.convert(sut));
    }

    @Test
    public void dslWithWithDefaultState() {

        NumericEqualsRuleDefinition ruleDefinition = new NumericEqualsRuleDefinition();
        ruleDefinition.setComparison(2);
        ruleDefinition.setVariable("$.size");
        ruleDefinition.setNext("SmallSizeState");

        sut.setChoices(Collections.singletonList(ruleDefinition));
        sut.setDefaultState("DefaultState");

        String expected = "{\n" +
                "  \"Type\" : \"Choice\",\n" +
                "  \"Choices\" : [ {\n" +
                "    \"Variable\" : \"$.size\",\n" +
                "    \"NumericEquals\" : 2,\n" +
                "    \"Next\" : \"SmallSizeState\"\n" +
                "  } ],\n" +
                "  \"Default\" : \"DefaultState\"\n" +
                "}";

        assertEquals(expected, object2JsonConverter.convert(sut));
    }
}