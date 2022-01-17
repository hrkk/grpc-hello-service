package com.example.workers2;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
@SpringBootApplication
public class InvadeWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvadeWorkerApplication.class, args);

        // subscribe to an external task topic as specified in the process
        /*
        client.subscribe("InvadePersia")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here
                    String command = externalTask.getVariable("command");
                    ApiJob apiJob = camundaCommands.find(command.split("api=")[1]);
                    log.info("INVADE PERSIA!!!"+apiJob.process("tenant"));
                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();

         */
    }
}
