package xyz.austinatchley.poesie.client;

import xyz.austinatchley.poesie.entity.ImageEntity;

public interface PoesieImageClient {

    /**
     * Generates an image response with only a topic string. Interlays the user topic into a system prompt
     * and sends the request to a backend API.
     * 
     * @param topic The topic or description of the image to generate, in a few words
     * @return The ImageEntity representing the structured image response from the backend model
     */
    ImageEntity generateImageResponse(String topic);

    /**
     * Generates an image response with a topic string and an associated poem. 
     * Interlays the user topic into a system prompt and sends the request to a backend API.
     * 
     * @param topic The topic or description of the image to generate, in a few words
     * @param poem The poem this image will be presented alongside
     * @return The ImageEntity representing the structured image response from the backend model
     */
    ImageEntity generateImageResponse(String topic, String poem);
}
