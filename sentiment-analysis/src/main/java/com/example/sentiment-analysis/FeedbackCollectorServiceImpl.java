package main.java.com.example.sentiment-analysis;

import io.grpc.stub.StreamObserver;

public class FeedbackCollectorServiceImpl extends FeedbackCollectorServiceGrpc.FeedbackCollectorServiceImplBase {
    @Override
    public StreamObserver<Feedback> submitFeedbacks(StreamObserver<FeedbackSummary> responseObserver) {
        return new StreamObserver<Feedback>() {
            private int totalFeedbacks = 0;
            private double totalScore = 0;
            
            @Override
            public void onNext(Feedback value) {
                totalFeedbacks++;
                totalScore += 0.75;  // Simulação de análise de sentimento
            }

            @Override
            public void onError(Throwable t) {}

            @Override
            public void onCompleted() {
                FeedbackSummary summary = FeedbackSummary.newBuilder()
                    .setTotalFeedbacks(totalFeedbacks)
                    .setAverageSentimentScore(totalScore / totalFeedbacks)
                    .build();
                responseObserver.onNext(summary);
                responseObserver.onCompleted();
            }
        };
    }
}
