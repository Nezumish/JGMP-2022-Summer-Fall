package com.rntgroup.rpc.protocols.grpc;

import com.rntgroup.rpc.protocols.grpc.proto.RequestMessage;
import com.rntgroup.rpc.protocols.grpc.proto.Type;
import com.rntgroup.rpc.protocols.grpc.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcJavaClientApplication implements CommandLineRunner {

    private static final String CLIENT_NAME = "JAVA_CLIENT";

    @Autowired
    private GrpcClientService grpcClient;

    public static void main(String[] args) {
        SpringApplication.run(GrpcJavaClientApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        var response = grpcClient.sendMessage(
                RequestMessage.newBuilder()
                        .setType(Type.LETTER)
                        .setMessage("Hello from Java!")
                        .setReceiver("GOLANG_SERVER")
                        .setSender(CLIENT_NAME)
                        .build()
        );

        System.out.println(response);

    }

}
