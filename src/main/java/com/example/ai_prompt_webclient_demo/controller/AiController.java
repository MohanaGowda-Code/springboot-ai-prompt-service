package com.example.ai_prompt_webclient_demo.controller;

import com.example.ai_prompt_webclient_demo.dto.AiResponse;
import com.example.ai_prompt_webclient_demo.service.AiPromptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AiController {

    private final AiPromptService aiPromptService;

    public AiController(AiPromptService aiPromptService) {
        this.aiPromptService = aiPromptService;
    }

    @GetMapping("/api/ai/ask")
    public Mono<AiResponse> askAI(@RequestParam String prompt) {
        return aiPromptService.sendPrompt(prompt);
    }
}
