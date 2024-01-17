package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.PromptTemplate;

@Log4j2
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {
    private final OpenAiChatClient aiClient;

    @GetMapping("/{topic}")
    public TopSong getMethodName(@PathVariable String topic) {
        log.info("GET /demo/{}", topic);

        BeanOutputParser<TopSong> parser = new BeanOutputParser<>(TopSong.class);
        Prompt prompt = generatePrompt(topic, parser);
        return sendRequest(prompt, parser);
    }

    private Prompt generatePrompt(final String topic, final BeanOutputParser<TopSong> parser) {
        PromptTemplate template = new PromptTemplate(
            """
            Write a short story composed of a haiku, a paragraph of text, and another haiku. The story theme is {topic}
            {format}
            """);
        template.add("topic", topic);
        template.add("format", parser.getFormat());
        template.setOutputParser(parser);

        return template.create();
    }

    private TopSong sendRequest(final Prompt prompt, final BeanOutputParser<TopSong> parser) {
        log.info("Sending OpenAI request with prompt: {}", prompt);
        ChatResponse response = aiClient.generate(prompt);
        String text = response.getGeneration().getContent();

        log.info("OpenAI response: {}", response);
        return parser.parse(text);
    }

}