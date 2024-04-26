package xyz.austinatchley.poesie.client;

import xyz.austinatchley.poesie.entity.PoemEntity;

public interface PoesieChatClient {

    /**
     * Given a topic, creates and hydrates a PoemEntity object using the response from a backend LLM API
     * 
     * @param topic The topic on which to write this poem
     * @return The PoemEntity representing the structured LLM response to the given topic
     */
    PoemEntity generatePoemResponse(String topic);
    
}
