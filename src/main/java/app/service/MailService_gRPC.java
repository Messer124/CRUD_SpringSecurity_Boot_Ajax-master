package app.service;

import com.example.grpc.MailServiceGrpc;
import com.example.grpc.MailServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class MailService_gRPC {

    public String sendEmail(String mail, String msg) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8082").usePlaintext().build();

        MailServiceGrpc.MailServiceBlockingStub stub = MailServiceGrpc.newBlockingStub(channel);

        MailServiceOuterClass.MailFromClientRequest request = MailServiceOuterClass.MailFromClientRequest
                .newBuilder()
                .setMail(mail)
                .setMessage(msg)
                .build();

        MailServiceOuterClass.MailResponse response = stub.sendEmailToUser(request);

        channel.shutdownNow();

        return response.toString();
    }

    public String sendGreetingEmail(String mail) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8082").usePlaintext().build();

        MailServiceGrpc.MailServiceBlockingStub stub = MailServiceGrpc.newBlockingStub(channel);

        MailServiceOuterClass.GreetingEmail request = MailServiceOuterClass.GreetingEmail
                .newBuilder().setMail(mail).build();

        MailServiceOuterClass.MailResponse response = stub.sendGreetingEmail(request);

        channel.shutdownNow();

        return response.toString();
    }

    public String sendEmailOnUpdating(String mail) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8082").usePlaintext().build();

        MailServiceGrpc.MailServiceBlockingStub stub = MailServiceGrpc.newBlockingStub(channel);

        MailServiceOuterClass.EmailOnUpdating request = MailServiceOuterClass.EmailOnUpdating
                .newBuilder().setMail(mail).build();

        MailServiceOuterClass.MailResponse response = stub.sendEmailOnUpdating(request);

        System.out.println("response = " + response);

        channel.shutdownNow();

        return response.toString();
    }

    public String sendEmailOnDeletion(String mail) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8082").usePlaintext().build();

        MailServiceGrpc.MailServiceBlockingStub stub = MailServiceGrpc.newBlockingStub(channel);

        MailServiceOuterClass.EmailOnDeletion request = MailServiceOuterClass.EmailOnDeletion
                .newBuilder().setMail(mail).build();

        MailServiceOuterClass.MailResponse response = stub.sendEmailOnDeletion(request);

        channel.shutdownNow();

        return response.toString();
    }
}
