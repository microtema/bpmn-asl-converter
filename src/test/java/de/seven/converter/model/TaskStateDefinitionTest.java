package de.seven.converter.model;

import de.seven.converter.enums.StateType;
import de.seven.converter.json.Object2JsonConverter;
import de.seven.fate.model.builder.annotation.Inject;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaskStateDefinitionTest {

    @Inject
    TaskStateDefinition sut;

    @Inject
    Object2JsonConverter object2JsonConverter;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void taskType() {

        assertSame(StateType.TASK, sut.getType());
    }

    @Test
    public void dsl() {

        sut.setResource("arn:aws:states:us-east-1:123456789012:activity:HelloWorld");
        sut.setTimeoutSeconds(300);
        sut.setHeartbeatSeconds(60);
        sut.setNext("NextState");

        String expected = "{\n" +
                "  \"Type\" : \"Task\",\n" +
                "  \"Resource\" : \"arn:aws:states:us-east-1:123456789012:activity:HelloWorld\",\n" +
                "  \"TimeoutSeconds\" : 300,\n" +
                "  \"HeartbeatSeconds\" : 60,\n" +
                "  \"Next\" : \"NextState\"\n" +
                "}";

        assertEquals(expected, object2JsonConverter.convert(sut));
    }
}