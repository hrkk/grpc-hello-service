package com.example.workers2;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.interceptor.auth.BasicAuthProvider;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InvadeWorker {

    private final CamundaCommands camundaCommands;

    public InvadeWorker(CamundaCommands camundaCommands) {
        this.camundaCommands = camundaCommands;
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(10000) // long polling timeout
                .addInterceptor(new BasicAuthProvider("CaptainHook", "Rum"))
                .build();


        // subscribe to an external task topic as specified in the process
        client.subscribe("Invade")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here
                    String businessKey = externalTask.getBusinessKey();
                    if("fail".equals(businessKey)) {
                        log.warn("FAILURE");
                        externalTaskService.handleFailure(externalTask, "errorMessage", "errorDetails", 0, 0);
                    } else if("error".equals(businessKey)) {
                      log.warn("ERROR");
                      externalTaskService.handleBpmnError(externalTask, "errorCode", "errorMessage");
                    } else {
                        String command = externalTask.getVariable("command");
                        CommandJob commandJob = camundaCommands.find(command.split("api=")[1]);
                        log.info("INVADE " + commandJob.process("Tenant") + "!!!");
                        // Complete the task
                        externalTaskService.complete(externalTask);
                    }
                })
                .open();
    }
}
