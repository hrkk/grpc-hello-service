package com.example.server;

import com.example.proto.lib.HelloReply;
import com.example.proto.lib.HelloRequest;
import com.example.proto.lib.MyServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

@Slf4j
@GRpcService
public class MyGrpcServiceImpl extends MyServiceGrpc.MyServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        log.debug("Grpc invoked");
        HelloReply reply = HelloReply.newBuilder().setMessage("Grpc says hello ==> " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
