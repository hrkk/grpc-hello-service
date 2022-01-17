package com.example.workflow;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.instance.StartEventImpl;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.Property;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Slf4j
public class LoggerDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        StartEventImpl startEventImpl = execution.getBpmnModelElementInstance().getModelInstance().getModelElementById("StartEvent_1");
        ExtensionElements startEventExtensionElements = startEventImpl.getExtensionElements();
        Collection<CamundaProperty> startEventProperties = startEventExtensionElements.getElementsQuery()
                .filterByType(CamundaProperties.class).singleResult().getCamundaProperties();


/*
        ServiceTask serviceTask = (ServiceTask) execution.getBpmnModelElementInstance();
        CamundaProperties camProperties = serviceTask.getExtensionElements().getElementsQuery().filterByType(CamundaProperties.class).singleResult();
*/
        for (CamundaProperty camProperty : startEventProperties)
        {
            String name = camProperty.getCamundaName();
            String value = camProperty.getCamundaValue();
            log.info("name={}, value={}",name, value);
        }

        ProcessDefinitionEntity processDefinition = ((ExecutionEntity) execution).getProcessDefinition();
        String id = processDefinition.getKey();
       log.info("ID="+execution.getId());
//        boolean usePreProd = (boolean) execution.getVariable("usePreProd");

  //      log.info("useProd={}, currentActivityName={}",usePreProd, currentActivityName);

    }
}
