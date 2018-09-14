package de.seven.converter.bpmn.util;

import de.seven.converter.bpmn.enums.EIPType;
import de.seven.converter.bpmn.enums.PropertyType;
import de.seven.converter.util.EnumUtil;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.BaseElement;
import org.camunda.bpm.model.bpmn.instance.CatchEvent;
import org.camunda.bpm.model.bpmn.instance.Documentation;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Gateway;
import org.camunda.bpm.model.bpmn.instance.InclusiveGateway;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.SubProcess;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.camunda.bpm.model.xml.type.attribute.Attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.seven.converter.bpmn.constants.Constants.MAY_NOT_BE_NULL;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@UtilityClass
public class ModelElementInstanceUtil {

    public static boolean isStartElement(FlowNode flowNode) {
        Validate.notNull(flowNode, MAY_NOT_BE_NULL, "flowNode");

        if ((flowNode instanceof Gateway) || (flowNode instanceof CatchEvent)) {
            return true;
        }

        EIPType camelDslType = findEipType(flowNode);

        return EIPType.MESSAGE_SPLITTER == camelDslType || EIPType.SUB_PROCESS == camelDslType;
    }

    public static EIPType findEipType(BaseElement baseElement) {

        String modelerTemplate = findModelerTemplate(baseElement);

        return EIPType.findByEipTypeName(modelerTemplate);
    }

    @SuppressWarnings("unchecked")
    public static <T> T findAttribute(BaseElement baseElement, String attributeName) {
        Validate.notNull(baseElement);
        Validate.notNull(attributeName);

        ModelElementType elementType = baseElement.getElementType();

        Attribute<T> attribute = (Attribute<T>) elementType.getAttribute(attributeName);

        if (attribute != null) {
            return attribute.getValue(baseElement);
        }

        return (T) baseElement.getAttributeValueNs("http://camunda.org/schema/1.0/bpmn", attributeName);
    }

    public static List<FlowNode> getStartElements(BpmnModelInstance bpmnModelInstance) {
        Validate.notNull(bpmnModelInstance);

        List<FlowNode> groups = new ArrayList<>();

        List<StartEvent> catchEvents = getModelElementsByType(bpmnModelInstance, StartEvent.class);
        List<Gateway> gateways = getModelElementsByType(bpmnModelInstance, Gateway.class);
        List<ServiceTask> tasks = getModelElementsByType(bpmnModelInstance, ServiceTask.class).stream().filter(ModelElementInstanceUtil::isStartElement).collect(Collectors.toList());
        List<SubProcess> subProcess = getModelElementsByType(bpmnModelInstance, SubProcess.class).stream().filter(ModelElementInstanceUtil::isStartElement).collect(Collectors.toList());

        //NOTE: Add First Inclusive Gateways that contains start throw events
        List<Gateway> startInclusiveGateWay = gateways.stream()
                .filter(it -> it instanceof InclusiveGateway)
                .filter(it -> it.getIncoming().stream().map(SequenceFlow::getSource).filter(s -> s instanceof CatchEvent).map(s -> (CatchEvent) s).filter(catchEvents::contains).count() > 0)
                .collect(Collectors.toList());

        gateways.removeAll(startInclusiveGateWay);
        groups.addAll(startInclusiveGateWay);

        groups.addAll(catchEvents);
        groups.addAll(gateways);
        groups.addAll(tasks);
        groups.addAll(subProcess);

        return groups;
    }

    /**
     * @param modelInstance the process model as stream
     * @param type          the ModelElementInstance type
     * @param <T>           ModelElementInstance Type
     * @return the model elements by type
     */
    @SuppressWarnings("unchecked")
    public static <T extends ModelElementInstance> List<T> getModelElementsByType(BpmnModelInstance modelInstance,
                                                                                  Class<T> type) {

        ModelElementType modelElementType = modelInstance.getModel().getType(type);

        Collection<ModelElementInstance> elementInstances = modelInstance.getModelElementsByType(modelElementType);

        return elementInstances.stream().map(elementInstance -> (T) elementInstance).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T> T findExtensionPropertyValue(BaseElement baseElement, PropertyType propertyName, Class<T> propertyType) {

        String propertyValue = findExtensionPropertyValue(baseElement, propertyName.name());

        boolean isNull = StringUtils.isBlank(propertyValue);

        if (Boolean.class.isAssignableFrom(propertyType)) {
            return (T) Boolean.valueOf(propertyValue);
        }

        if (Integer.class.isAssignableFrom(propertyType)) {

            return (T) (isNull ? null : Integer.valueOf(propertyValue));
        }

        if (Long.class.isAssignableFrom(propertyType)) {
            return (T) (isNull ? null : Long.valueOf(propertyValue));
        }

        if (propertyType.isEnum()) {
            return (T) EnumUtil.findOne((Enum[]) propertyType.getEnumConstants(), propertyValue);
        }

        return (T) propertyValue;
    }

    public static String findExtensionPropertyValue(BaseElement baseElement, String propertyName) {
        notNull(baseElement);
        notEmpty(propertyName);

        ExtensionElements extensionElements = baseElement.getExtensionElements();

        if (extensionElements == null) {
            return null;
        }

        Collection<ModelElementInstance> elements = extensionElements.getElements();

        return getExtensionPropertyValue(elements, propertyName);
    }

    private static String findModelerTemplate(BaseElement baseElement) {

        return findAttribute(baseElement, "modelerTemplate");
    }

    private static String getExtensionPropertyValue(Collection<ModelElementInstance> elements, String propertyName) {
        assert elements != null;
        assert propertyName != null;

        for (ModelElementInstance modelElementInstance : elements) {

            if (!(modelElementInstance instanceof CamundaProperties)) {
                continue;
            }

            CamundaProperties camundaProperties = (CamundaProperties) modelElementInstance;

            for (CamundaProperty camundaProperty : camundaProperties.getCamundaProperties()) {
                if (StringUtils.equalsIgnoreCase(camundaProperty.getCamundaName(), propertyName)) {
                    return camundaProperty.getCamundaValue();
                }
            }
        }

        return null;
    }

    public static String getVersionTag(Process process) {
        notNull(process, MAY_NOT_BE_NULL, "process");

        return process.getAttributeValueNs("http://camunda.org/schema/1.0/bpmn", "versionTag");
    }

    public static void setVersionTag(Process process, String versionTag) {
        notNull(process, MAY_NOT_BE_NULL, "process");

        process.setAttributeValueNs("http://camunda.org/schema/1.0/bpmn", "versionTag", versionTag);
    }

    public static String getTextDocumentation(Process process) {
        notNull(process, MAY_NOT_BE_NULL, "process");

        Collection<Documentation> childElementsByType = process.getChildElementsByType(Documentation.class);

        Optional<Documentation> optional = childElementsByType.stream().findFirst();

        return optional.map(ModelElementInstance::getTextContent).orElse(null);
    }

    public static String getContentText(BaseElement baseElement) {
        notNull(baseElement, MAY_NOT_BE_NULL, "baseElement");

        return findAttribute(baseElement, "delegateExpression");
    }
}
