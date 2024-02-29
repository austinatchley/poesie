package xyz.austinatchley.poesie;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import xyz.austinatchley.poesie.client.PoesieChatClient;
import xyz.austinatchley.poesie.client.PoesieImageClient;
import xyz.austinatchley.poesie.entity.ImageEntity;
import xyz.austinatchley.poesie.entity.MultimediaEntity;
import xyz.austinatchley.poesie.entity.PoemEntity;

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
        ImageEntity imageEntity = imageClient.generateImageResponse(topic, poemEntity.prose());

        return MultimediaEntity.builder()
                .chatResponse(poemEntity.toString())
                .imageResponse(imageEntity.toString())
                .build();
    }
}