package main.java.com.example.sentiment-analysis;

import io.grpc.stub.StreamObserver;

public class SentimentAnalyzerServiceImpl extends SentimentAnalyzerServiceGrpc.SentimentAnalyzerServiceImplBase {

    @Override
    public void analyzeText(TextRequest request, StreamObserver<SentimentResponse> responseObserver) {
        // Simulação de análise de sentimento (retorna "positivo" como resultado fixo)
        String sentiment = "positive";  // Exemplo fixo
        double score = 0.85;  // Exemplo fixo

        SentimentResponse response = SentimentResponse.newBuilder()
                .setSentiment(sentiment)
                .setScore(score)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<TextRequest> streamLiveSentiment(StreamObserver<SentimentUpdate> responseObserver) {
        return new StreamObserver<TextRequest>() {

            @Override
            public void onNext(TextRequest value) {
                // Começando a análise
                System.out.println("Analisando o texto: " + value.getText());

                // Simulação de análise em etapas, enviando atualizações ao cliente
                // Vamos simular um processo de análise com várias etapas

                // Envia uma atualização inicial de "Processando"
                SentimentUpdate update = SentimentUpdate.newBuilder()
                        .setStatus("Processing")
                        .setReliability(0.2) // Confiabilidade baixa no início
                        .build();
                responseObserver.onNext(update);

                // Simulando um pequeno atraso (pode ser substituído por lógica real de análise)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Envia uma segunda atualização após processamento parcial
                update = SentimentUpdate.newBuilder()
                        .setStatus("Still processing")
                        .setReliability(0.5) // Maior confiabilidade
                        .build();
                responseObserver.onNext(update);

                // Outro pequeno atraso
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Envia uma última atualização quando a análise está concluída
                update = SentimentUpdate.newBuilder()
                        .setStatus("Completed")
                        .setReliability(1.0) // Alta confiabilidade
                        .build();
                responseObserver.onNext(update);

                // Finaliza a comunicação
                responseObserver.onCompleted();
            }

            @Override
            public void onError(Throwable t) {
                // Tratamento de erro (se necessário)
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                // Quando o fluxo é completado
                System.out.println("Análise concluída.");
            }
        };
    }
}
