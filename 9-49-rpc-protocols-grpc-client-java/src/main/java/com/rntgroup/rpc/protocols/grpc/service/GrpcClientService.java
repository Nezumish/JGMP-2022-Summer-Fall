package com.rntgroup.rpc.protocols.grpc.service;

import com.rntgroup.rpc.protocols.grpc.proto.MessagingServiceGrpc;
import com.rntgroup.rpc.protocols.grpc.proto.RequestMessage;
import com.rntgroup.rpc.protocols.grpc.proto.ResponseMessage;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {

    @GrpcClient("local-grpc-server")
    private MessagingServiceGrpc.MessagingServiceBlockingStub stub;

    public ResponseMessage sendMessage(RequestMessage requestMessage) {
        ResponseMessage response;

        try {
            response = stub.sendMessage(requestMessage);
        } catch (Exception e) {
            response = buildErrorResponse(e.getMessage());
        }

        return response;
    }

    private ResponseMessage buildErrorResponse(String errorMessage) {
        return ResponseMessage.newBuilder()
                .setMessage(errorMessage)
                .build();
    }

}
