package de.seven.converter.bpmn;

import de.seven.converter.Converter;
import de.seven.converter.bpmn.enums.PropertyType;
import de.seven.converter.bpmn.model.StateBuilderContext;
import de.seven.converter.bpmn.model.StatePatternData;
import de.seven.converter.bpmn.pattern.BpmnModelInstance2StatePatternsConverter;
import de.seven.converter.model.StateBuilder;
import org.apache.commons.lang3.Validate;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.List;

import static de.seven.converter.bpmn.constants.Constants.MAY_NOT_BE_NULL;
import static de.seven.converter.bpmn.util.ModelElementInstanceUtil.findExtensionPropertyValue;
import static de.seven.converter.bpmn.util.ModelElementInstanceUtil.getTextDocumentation;
import static de.seven.converter.bpmn.util.ModelElementInstanceUtil.getVersionTag;

public class BpmnModelInstance2StateBuilderConverter implements Converter<BpmnModelInstance, StateBuilder> {

    private final BpmnModelInstance2StatePatternsConverter bpmnModelInstance2StatePatternsConverter;
    private final BpmnModelInstance2StateBuilderContextConverter bpmnModelInstance2StateBuilderContextConverter;
    private final StatePatternData2StateDefinitionStrategy statePatternData2StateDefinitionStrategy;

    public BpmnModelInstance2StateBuilderConverter(BpmnModelInstance2StatePatternsConverter bpmnModelInstance2StatePatternsConverter,
                                                   BpmnModelInstance2StateBuilderContextConverter bpmnModelInstance2StateBuilderContextConverter,
                                                   StatePatternData2StateDefinitionStrategy statePatternData2StateDefinitionStrategy) {
        this.bpmnModelInstance2StatePatternsConverter = bpmnModelInstance2StatePatternsConverter;
        this.bpmnModelInstance2StateBuilderContextConverter = bpmnModelInstance2StateBuilderContextConverter;
        this.statePatternData2StateDefinitionStrategy = statePatternData2StateDefinitionStrategy;
    }

    @Override
    public void update(BpmnModelInstance source, StateBuilder target) {
        Validate.notNull(source, MAY_NOT_BE_NULL, "source");
        Validate.notNull(target, MAY_NOT_BE_NULL, "target");

        StateBuilderContext context = bpmnModelInstance2StateBuilderContextConverter.convert(source, target);

        Process process = Validate.notNull(context.getProcess(), MAY_NOT_BE_NULL, "process");

        target.setName(process.getId());
        target.setVersion(getVersionTag(process));
        target.setDescription(getTextDocumentation(process));
        target.setTimeoutSeconds(findExtensionPropertyValue(process, PropertyType.TIMEOUT, Integer.class));

        List<StatePatternData> statePatterns = bpmnModelInstance2StatePatternsConverter.convert(source);
        statePatternData2StateDefinitionStrategy.convertToList(statePatterns, context);
    }

    @Override
    public StateBuilder createInstance() {

        return new StateBuilder();
    }
}
