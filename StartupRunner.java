package com.assignment.runner;

import com.assignment.model.WebhookResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class StartupRunner implements CommandLineRunner {

    private final WebClient webClient = WebClient.builder().build();

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Application started. Sending generateWebhook request...");

        String requestBody = """
        {
          "name": "YOUR_NAME",
          "regNo": "YOUR_REG_NO",
          "email": "YOUR_EMAIL"
        }
        """;

        Mono<WebhookResponse> respMono = webClient.post()
                .uri("https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(WebhookResponse.class);

        WebhookResponse response = respMono.block();

        if (response == null) {
            System.out.println("Error: No response received.");
            return;
        }

        System.out.println("Webhook URL: " + response.getWebhookUrl());
        System.out.println("Access Token: Received");

        String finalQuery = "<PUT YOUR FINAL SQL QUERY HERE>";

        sendFinalQuery(response.getWebhookUrl(), response.getAccessToken(), finalQuery);
    }

    private void sendFinalQuery(String webhookUrl, String accessToken, String query) {
        System.out.println("Sending final SQL query...");

        String body = "{\"finalQuery\": \"" + query.replace(""", "\\"") + "\"}";

        String resp = webClient.post()
                .uri(webhookUrl)
                .header("Authorization", accessToken)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Response from webhook: " + resp);
    }
}
