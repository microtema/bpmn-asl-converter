package de.seven.converter.model;

import de.seven.converter.enums.StateType;
import de.seven.converter.json.Object2JsonConverter;
import de.seven.fate.model.builder.annotation.Inject;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SucceedStateDefinitionTest {

    @Inject
    SucceedStateDefinition sut;

    @Inject
    Object2JsonConverter object2JsonConverter;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void taskType() {

        assertSame(StateType.SUCCEED, sut.getType());
    }

    @Test
    public void dsl() {

        String expected = "{\n" +
                "  \"Type\" : \"Succeed\"\n" +
                "}";

        assertEquals(expected, object2JsonConverter.convert(sut));
    }
}