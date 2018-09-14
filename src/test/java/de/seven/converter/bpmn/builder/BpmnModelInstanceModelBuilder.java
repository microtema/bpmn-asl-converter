package de.seven.converter.bpmn.builder;

import de.seven.fate.model.builder.AbstractModelBuilder;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import java.io.InputStream;

public class BpmnModelInstanceModelBuilder extends AbstractModelBuilder<BpmnModelInstance> {

    @Override
    public BpmnModelInstance fromResource(String resourceLocation) {

        InputStream inputStream = getClass().getResourceAsStream(resourceLocation);

        return Bpmn.readModelFromStream(inputStream);
    }
}