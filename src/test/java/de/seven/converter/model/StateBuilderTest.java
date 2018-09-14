package de.seven.converter.model;

import de.seven.converter.json.Object2JsonConverter;
import de.seven.fate.model.builder.annotation.Inject;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StateBuilderTest {

    @Inject
    StateBuilder sut;

    @Inject
    Object2JsonConverter object2JsonConverter;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void dsl() {

        sut.setDescription("Some Description");
        sut.setVersion("1.0");

        TaskStateDefinition stateDefinition = new TaskStateDefinition();
        stateDefinition.setName("ConvertTask");
        sut.addState(stateDefinition);

        stateDefinition = new TaskStateDefinition();
        stateDefinition.setName("Done");
        sut.addState(stateDefinition);

        String expected = "{\n" +
                "  \"Comment\" : \"Some Description\",\n" +
                "  \"StartAt\" : \"ConvertTask\",\n" +
                "  \"Version\" : \"1.0\",\n" +
                "  \"States\" : {\n" +
                "    \"ConvertTask\" : {\n" +
                "      \"Type\" : \"Task\"\n" +
                "    },\n" +
                "    \"Done\" : {\n" +
                "      \"Type\" : \"Task\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        assertEquals(expected, object2JsonConverter.convert(sut));
    }
}