package xyz.austinatchley.poesie.client;

import org.springframework.ai.openai.OpenAiImageClient;
import org.springframework.ai.openai.OpenAiImageOptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import xyz.austinatchley.poesie.entity.ImageEntity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.NonNull;

@Component
@Log4j2
@RequiredArgsConstructor
public class PoesieOpenAiImageClient implements PoesieImageClient {
    private static final ImageOptions DEFAULT_IMAGE_OPTIONS = OpenAiImageOptions.builder()
            .withResponseFormat("url")
            .build();

    private final OpenAiImageClient imageClient;

    @Override
    public ImageEntity generateImageResponse(@NonNull final String topic) {
        final ImagePrompt prompt = generatePrompt(topic, null);
        return sendRequest(prompt);
    }
    
    @Override
    public ImageEntity generateImageResponse(@NonNull final String topic, @NonNull final String poem) {
        final ImagePrompt prompt = generatePrompt(topic, poem);
        return sendRequest(prompt);
    }

    private ImagePrompt generatePrompt(@NonNull final String topic, @Nullable final String poem) {

        String templateText = """
                Create an image to accompany a haiku in an ancient temple hidden in the misty forest.
                The image should be in the traditional Japanese style, created by a master of the medium
                after 100 years of pondering the haiku.
                The theme for both the haiku and image is: {topic}.
                """;

        if (StringUtils.isNotBlank(poem)) {
            templateText += """
                    The poem is:
                    {text}
                    """;
        }

        final PromptTemplate template = new PromptTemplate(templateText);
        template.add("topic", topic);

        if (StringUtils.isNotBlank(poem)) {
            template.add("text", poem);
        }

        final String promptText = template.create().getContents();
        log.info("Generated prompt: {}", promptText);

        return new ImagePrompt(promptText, DEFAULT_IMAGE_OPTIONS);
    }

    private ImageEntity sendRequest(@NonNull final ImagePrompt prompt) {
        log.info("Sending OpenAI request with prompt: {}", prompt);

        ImageResponse response = imageClient.call(prompt);
        log.info("OpenAI response: {}", response);

        ImageGeneration imageGeneration = response.getResult();
        log.info("Extracted image generation from response: {}", imageGeneration);

        log.info(imageGeneration.getOutput());

        final Image image = imageGeneration.getOutput();
        final String url = image.getUrl();
        final String metadata = imageGeneration.getMetadata().toString();

        return new ImageEntity(url, metadata);
    }
}
