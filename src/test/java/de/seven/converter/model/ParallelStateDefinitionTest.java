package de.seven.converter.model;

import de.seven.converter.enums.StateType;
import de.seven.converter.json.Object2JsonConverter;
import de.seven.fate.model.builder.annotation.Inject;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class ParallelStateDefinitionTest {

    @Inject
    ParallelStateDefinition sut;

    @Inject
    Object2JsonConverter object2JsonConverter;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void taskType() {

        assertSame(StateType.PARALLEL, sut.getType());
    }

    @Test
    public void dsl() {

        sut.setNext("Final State");

        StateBuilder stateBuilder = new StateBuilder();

        TaskStateDefinition stateDefinition = new TaskStateDefinition();
        stateDefinition.setName("ConvertTask");
        stateBuilder.addState(stateDefinition);

        stateDefinition = new TaskStateDefinition();
        stateDefinition.setName("Done");
        stateBuilder.addState(stateDefinition);

        sut.setBranches(Collections.singletonList(stateBuilder));

        String expected = "{\n" +
                "  \"Type\" : \"Parallel\",\n" +
                "  \"Branches\" : [ {\n" +
                "    \"StartAt\" : \"ConvertTask\",\n" +
                "    \"States\" : {\n" +
                "      \"ConvertTask\" : {\n" +
                "        \"Type\" : \"Task\"\n" +
                "      },\n" +
                "      \"Done\" : {\n" +
                "        \"Type\" : \"Task\"\n" +
                "      }\n" +
                "    }\n" +
                "  } ],\n" +
                "  \"Next\" : \"Final State\"\n" +
                "}";

        assertEquals(expected, object2JsonConverter.convert(sut));
    }
}