package main.java.com.example.sentiment-analysis;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class SentimentAnalyzerClient {
    public static void main(String[] args) {
        // Conectar ao servidor gRPC
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // Sem criptografia
                .build();

        // Criando o stub para o SentimentAnalyzerService
        SentimentAnalyzerServiceGrpc.SentimentAnalyzerServiceStub sentimentAnalyzerStub = 
            SentimentAnalyzerServiceGrpc.newStub(channel);

        // Enviar o texto para análise
        TextRequest request = TextRequest.newBuilder().setText("I am very happy with the service!").build();

        // Cliente que recebe as respostas do servidor
        sentimentAnalyzerStub.streamLiveSentiment(request, new StreamObserver<SentimentUpdate>() {
            @Override
            public void onNext(SentimentUpdate value) {
                System.out.println("Status: " + value.getStatus());
                System.out.println("Reliability: " + value.getReliability());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Análise concluída.");
            }
        });

        // Fechar o canal após o teste
        try {
            Thread.sleep(5000); // Aguarde para o servidor processar as atualizações
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        channel.shutdown();
    }
}
