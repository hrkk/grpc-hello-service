package com.example.workers2;

public interface CommandJob {

    String getApi();

    String process(String input);

}
