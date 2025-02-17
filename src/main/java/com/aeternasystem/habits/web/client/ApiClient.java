package com.aeternasystem.habits.web.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);
    private final WebClient webClient;
    private static final String LOG_FORMAT = "{}: {}";

    public ApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public void sendGetRequest(String url, String successMessage, String errorMessage) {
        webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> logger.info(LOG_FORMAT, successMessage, response))
                .doOnError(error -> logger.error(LOG_FORMAT, errorMessage, error.getMessage()))
                .subscribe();
    }

    public void sendPostRequest(String url, Object bodyValue, String successMessage, String errorMessage) {
        webClient.post()
                .uri(url)
                .bodyValue(bodyValue)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> logger.info(LOG_FORMAT, successMessage, response))
                .doOnError(error -> logger.error(LOG_FORMAT, errorMessage, error.getMessage()))
                .subscribe();
    }
}
