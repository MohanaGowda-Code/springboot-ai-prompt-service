package com.example.ai_prompt_webclient_demo.service;

import com.example.ai_prompt_webclient_demo.dto.AiRequest;
import com.example.ai_prompt_webclient_demo.dto.AiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class AiPromptService {

    private static final Logger logger = LoggerFactory.getLogger(AiPromptService.class);

    private final WebClient webClient;
    //private final Counter retryCounter;


    public AiPromptService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<AiResponse> sendPrompt(String prompt) {
        AiRequest request = new AiRequest("gpt-4.1-mini", prompt);

        Mono<String> aiCall = webClient.post()
                .uri("/completions")
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        res -> Mono.error(new RuntimeException("Client Error: " + res.statusCode())))
                .onStatus(status -> status.is5xxServerError(),
                        res -> Mono.error(new RuntimeException("Server Error: " + res.statusCode())))
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(1))
                .doOnSubscribe(sub -> logger.info("Sending prompt: {}", prompt))
                .doOnSuccess(resp -> logger.info("AI Response received"))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(3))
                        .doBeforeRetry(rs -> {
                            logger.warn("Retrying due to: {}", rs.totalRetries() + 1,rs.failure().getMessage());
                        }))
                .onErrorResume(e -> {
                    logger.error("Error calling AI API: {}", e.getMessage());
                    logger.error("AI service failed after retries", e);
                    return Mono.just("AI service is temporarily unavailable. Please try again later. " + e.getMessage());
                });

        return aiCall.map(AiResponse::new);
    }
}
