package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ai.openai.OpenAiChatClient;

@RestController
@RequestMapping("/demo")
@Log4j2
@RequiredArgsConstructor
public class DemoController {
    private final OpenAiChatClient aiClient;

    @GetMapping("/{topic}")
    public String getMethodName(@PathVariable String topic) {
        log.info("GET /demo/{}", topic);

        String prompt = "Write a short story composed of a haiku, a paragraph of text, and another haiku. The story theme is " + topic;
        log.info("Sending OpenAI request with prompt: {}", prompt);
        String response = aiClient.generate(prompt);

        log.info("OpenAI response: {}", response);
        return response;
    }
    
}