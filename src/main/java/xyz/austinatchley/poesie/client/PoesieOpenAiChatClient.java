package xyz.austinatchley.poesie.client;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.stereotype.Component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import xyz.austinatchley.poesie.entity.PoemEntity;

@Component
@Log4j2
@RequiredArgsConstructor
public class PoesieOpenAiChatClient implements PoesieChatClient {
    private final BeanOutputParser<PoemEntity> parser = new BeanOutputParser<>(PoemEntity.class);
    private final OpenAiChatClient chatClient;

    @Override
    public PoemEntity generatePoemResponse(@NonNull final String topic) {

        final Prompt prompt = generatePrompt(topic);
        return sendRequest(prompt);
    }

    private Prompt generatePrompt(@NonNull final String topic) {
        PromptTemplate template = new PromptTemplate(
                """
                        You are a helpful assistant to a master poet in ancient Japan. If you fail at the following task
                        you won't be allowed to continue in your workshop, so be sure to demonstrate an enlightened view 
                        of the world, using witty insights and thought provoking wordplay. Please perform the following:

                        Write a cohesive text composed of a title, a haiku, and a paragraph of text. The three elements 
                        should complement each other to achieve a single artistic idea. The story theme is: {topic}

                        Additionally, write descriptions for 3 traditional Japanese illustrations to appear alongside your text in the master's workshop.

                        {format}
                        """);
        template.add("topic", topic);
        template.add("format", this.parser.getFormat());
        template.setOutputParser(this.parser);

        return template.create();
    }

    private PoemEntity sendRequest(@NonNull final Prompt prompt) {
        log.info("Sending OpenAI request with prompt: {}", prompt);

        ChatResponse response = chatClient.call(prompt);
        log.info("OpenAI response: {}", response);

        String text = response.getResult().getOutput().getContent();
        return this.parser.parse(text);
    }
}
