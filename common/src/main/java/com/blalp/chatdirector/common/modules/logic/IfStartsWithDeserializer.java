package com.blalp.chatdirector.common.modules.logic;

import java.io.IOException;

import com.blalp.chatdirector.core.configuration.Chain;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class IfStartsWithDeserializer extends JsonDeserializer<IfStartsWithItem> {

    @Override
    public IfStartsWithItem deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        IfStartsWithItem output = new IfStartsWithItem();
        if (config.has("yes-chain")) {
            output.yesChain = config.get("yes-chain").traverse(oc).readValueAs(Chain.class);
        }
        if (config.has("no-chain")) {
            output.noChain = config.get("no-chain").traverse(oc).readValueAs(Chain.class);
        }
        if (config.has("starts") && config.get("starts").isTextual()) {
            output.starts = config.get("starts").asText();
        }
        if (config.has("source") && config.get("source").isTextual()) {
            output.source = config.get("source").asText();
        }
        return output;
    }

}
