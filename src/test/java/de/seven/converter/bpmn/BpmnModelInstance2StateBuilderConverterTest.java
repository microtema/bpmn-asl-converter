package de.seven.converter.bpmn;

import de.seven.converter.bpmn.builder.BpmnModelInstanceModelBuilder;
import de.seven.converter.json.Object2JsonConverter;
import de.seven.converter.model.StateBuilder;
import de.seven.fate.model.builder.annotation.Inject;
import de.seven.fate.model.builder.annotation.Model;
import de.seven.fate.model.builder.enums.ModelType;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BpmnModelInstance2StateBuilderConverterTest {

    @Inject
    BpmnModelInstance2StateBuilderConverter sut;

    @Inject
    BpmnModelInstanceModelBuilder builder;

    @Inject
    Object2JsonConverter object2JsonConverter;

    @Model(type = ModelType.SOURCE, resource = "/step-function/workflow.bpmn")
    BpmnModelInstance bpmnModelInstance;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void convert() {

        StateBuilder stateBuilder = sut.convert(bpmnModelInstance);

        assertNotNull(stateBuilder);

        assertEquals("{\n" +
                "  \"Comment\" : \"Some Description\",\n" +
                "  \"StartAt\" : \"Task_1vsgywb\",\n" +
                "  \"Version\" : \"1.0\",\n" +
                "  \"TimeoutSeconds\" : 20,\n" +
                "  \"States\" : {\n" +
                "    \"Task_1vsgywb\" : {\n" +
                "      \"Type\" : \"Task\",\n" +
                "      \"Resource\" : \"arn:aws:lambda:us-east-1:123456789012:function:Foo\",\n" +
                "      \"ResultPath\" : \"$\",\n" +
                "      \"Next\" : \"Task_0gq4et6\"\n" +
                "    },\n" +
                "    \"Task_0gq4et6\" : {\n" +
                "      \"Type\" : \"Task\",\n" +
                "      \"Resource\" : \"arn:aws:lambda:us-east-1:123456789012:function:Bar\",\n" +
                "      \"END\" : true\n" +
                "    }\n" +
                "  }\n" +
                "}", object2JsonConverter.convert(stateBuilder));
    }
}