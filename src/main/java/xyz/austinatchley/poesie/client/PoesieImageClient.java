package xyz.austinatchley.poesie.client;

import xyz.austinatchley.poesie.entity.ImageEntity;

public interface PoesieImageClient {

    /**
     * TODO
     * 
     * @param topic
     * @return
     */
    ImageEntity generateImageResponse(String topic);

    /**
     * TODO
     * 
     * @param topic
     * @param poem
     * @return
     */
    ImageEntity generateImageResponse(String topic, String poem);
}
