package main.java.com.example.sentiment-analysis;

import io.grpc.stub.StreamObserver;

public class AlertServiceImpl extends AlertServiceGrpc.AlertServiceImplBase {
    @Override
    public StreamObserver<AlertMessage> alertChannel(StreamObserver<AlertMessage> responseObserver) {
        return new StreamObserver<AlertMessage>() {
            @Override
            public void onNext(AlertMessage value) {
                AlertMessage alert = AlertMessage.newBuilder()
                    .setAlertType("Critical")
                    .setMessage("Urgent Attention Needed")
                    .build();
                responseObserver.onNext(alert);
            }

            @Override
            public void onError(Throwable t) {}

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
