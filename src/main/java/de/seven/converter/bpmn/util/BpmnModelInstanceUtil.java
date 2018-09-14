package de.seven.converter.bpmn.util;

import de.seven.converter.bpmn.constants.Constants;
import lombok.experimental.UtilityClass;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.Collection;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

@UtilityClass
public class BpmnModelInstanceUtil {

    public static String getProcessName(BpmnModelInstance bpmnModelInstance) {

        return getFirstProcess(bpmnModelInstance).getName();
    }

    public static String getProcessId(BpmnModelInstance bpmnModelInstance) {

        return getFirstProcess(bpmnModelInstance).getId();
    }

    public static Process getFirstProcess(BpmnModelInstance bpmnModelInstance) {
        notNull(bpmnModelInstance, Constants.MAY_NOT_BE_NULL, "bpmnModelInstance");

        Collection<Process> modelElementsByType = bpmnModelInstance.getModelElementsByType(Process.class);

        Optional<Process> optional = modelElementsByType.stream().findFirst();

        return optional.orElseThrow(() -> new IllegalArgumentException("Unable to get Process!"));
    }
}
