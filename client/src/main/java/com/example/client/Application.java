package com.example.client;

import com.example.proto.lib.HelloReply;
import com.example.proto.lib.HelloRequest;
import com.example.proto.lib.MyServiceGrpc;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

    @GrpcClient("grpc-server")
    private MyServiceGrpc.MyServiceBlockingStub simpleStub;

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    // http://localhost:8082/?name=kasper
    @RequestMapping("/")
    public String printMessage(@RequestParam(defaultValue = "Kim") String name) {
        try {
            final HelloReply response = this.simpleStub.sayHello(HelloRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }
}
