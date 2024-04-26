package xyz.austinatchley.poesie;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import xyz.austinatchley.poesie.client.PoesieChatClient;
import xyz.austinatchley.poesie.client.PoesieImageClient;
import xyz.austinatchley.poesie.entity.ImageEntity;
import xyz.austinatchley.poesie.entity.MultimediaEntity;
import xyz.austinatchley.poesie.entity.PoemEntity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RestController
@RequestMapping("/generate")
@RequiredArgsConstructor
public class PoesieController {
    private final PoesieChatClient chatClient;
    private final PoesieImageClient imageClient;

    @GetMapping("/text")
    public PoemEntity generateTextResponse(@RequestParam String topic) {
        log.info("GET /generate/text?topic={}", topic);
        return chatClient.generatePoemResponse(topic);
    }

    @GetMapping("/image")
    public ImageEntity generateImageResponse(@RequestParam String topic) {
        log.info("GET /generate/image?topic={}", topic);
        return imageClient.generateImageResponse(topic);
    }

    @GetMapping
    public MultimediaEntity generateMultimediaResponse(@RequestParam String topic) {
        log.info("GET /generate?topic={}", topic);
        PoemEntity poemEntity = chatClient.generatePoemResponse(topic);

        List<String> imageEntities = new ArrayList<>();
        for (String illustrationPrompt : poemEntity.illustrationPrompts()) {
            ImageEntity imageEntity = imageClient.generateImageResponse(illustrationPrompt, poemEntity.prose());
            imageEntities.add(imageEntity.toString());
        }

        return MultimediaEntity.builder()
                .chatResponse(poemEntity.toString())
                .imageResponse(imageEntities)
                .build();
    }
}