package com.example.workers2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CamundaCommands {

    Collection<CommandJob> all() {
        Collection<CommandJob> commandJobs = items.values();
        return commandJobs;
    }

    CommandJob find(String name) {
        return items.get(name);
    }

    private final Map<String, CommandJob> items = new HashMap<String, CommandJob>() {
        void append(CommandJob job) {
            put(job.getApi(), job);
        }

        {
            append(new CommandJob() {
                @Override
                public String getApi() {
                    return "api1";
                }

                @Override
                public String process(String input) {
                    log.info("Invade Persia :: api={}", this.getApi());
                    return "Persia";
                }
            });
            append(new CommandJob() {
                @Override
                public String getApi() {
                    return "api2";
                }

                @Override
                public String process(String input) {
                    log.info("Invade Goul :: api={}", this.getApi());
                    return "Goul";
                }
            });
        }

    };
}
