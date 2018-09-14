package de.seven.converter.model;

import de.seven.converter.enums.StateType;
import de.seven.converter.json.Object2JsonConverter;
import de.seven.fate.model.builder.annotation.Inject;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class PassStateDefinitionTest {

    @Inject
    PassStateDefinition sut;

    @Inject
    Object2JsonConverter object2JsonConverter;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void taskType() {

        assertSame(StateType.PASS, sut.getType());
    }

    @Test
    public void dsl() {

        sut.setResult(Collections.singletonMap("x-datum", 0.381018D));
        sut.setResultPath("$.coords");
        sut.setNext("End");

        String expected = "{\n" +
                "  \"Type\" : \"Pass\",\n" +
                "  \"Result\" : {\n" +
                "    \"x-datum\" : 0.381018\n" +
                "  },\n" +
                "  \"ResultPath\" : \"$.coords\",\n" +
                "  \"Next\" : \"End\"\n" +
                "}";

        assertEquals(expected, object2JsonConverter.convert(sut));
    }
}