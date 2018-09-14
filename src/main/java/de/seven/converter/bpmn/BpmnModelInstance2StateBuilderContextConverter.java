package de.seven.converter.bpmn;

import de.seven.converter.ExtendedConverter;
import de.seven.converter.bpmn.model.StateBuilderContext;
import de.seven.converter.model.StateBuilder;
import org.apache.commons.lang3.Validate;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import static de.seven.converter.bpmn.constants.Constants.MAY_NOT_BE_NULL;
import static de.seven.converter.bpmn.util.BpmnModelInstanceUtil.getFirstProcess;

public class BpmnModelInstance2StateBuilderContextConverter implements ExtendedConverter<BpmnModelInstance, StateBuilderContext, StateBuilder> {

    @Override
    public void update(BpmnModelInstance source, StateBuilderContext target, StateBuilder stateBuilder) {
        Validate.notNull(source, MAY_NOT_BE_NULL, "source");
        Validate.notNull(target, MAY_NOT_BE_NULL, "target");

        target.setBpmnModelInstance(source);
        target.setProcess(getFirstProcess(source));
        target.setStateBuilder(stateBuilder);
    }

    @Override
    public StateBuilderContext createInstance() {

        return new StateBuilderContext();
    }
}
