package xyz.austinatchley.poesie.client;

import xyz.austinatchley.poesie.entity.PoemEntity;

public interface PoesieChatClient {

    /**
     * TODO
     * @param topic
     * @return
     */
    PoemEntity generatePoemResponse(String topic);
    
}
